package com.bvrit.cierclibrarymanagementsystem.servicelayer.impl;

import com.bvrit.cierclibrarymanagementsystem.Transformers.BookAndUserAuditTrialTransformer;
import com.bvrit.cierclibrarymanagementsystem.Transformers.UserTransformer;
import com.bvrit.cierclibrarymanagementsystem.dtos.responsedtos.BookAndUserAuditTrialResponse;
import com.bvrit.cierclibrarymanagementsystem.dtos.responsedtos.BookResponse;
import com.bvrit.cierclibrarymanagementsystem.dtos.responsedtos.UserResponse;
import com.bvrit.cierclibrarymanagementsystem.enums.BookAndUserAuditStatus;
import com.bvrit.cierclibrarymanagementsystem.enums.BookStatus;
import com.bvrit.cierclibrarymanagementsystem.enums.CardStatus;
import com.bvrit.cierclibrarymanagementsystem.exceptions.BookCannotBeIssuedException;
import com.bvrit.cierclibrarymanagementsystem.exceptions.UserBookIssueMismatchException;
import com.bvrit.cierclibrarymanagementsystem.generators.EmailGenerator;
import com.bvrit.cierclibrarymanagementsystem.models.Book;
import com.bvrit.cierclibrarymanagementsystem.models.BookAndUserAuditTrial;
import com.bvrit.cierclibrarymanagementsystem.models.Card;
import com.bvrit.cierclibrarymanagementsystem.models.User;
import com.bvrit.cierclibrarymanagementsystem.repositorylayer.BookAndUserAuditTrialRepository;
import com.bvrit.cierclibrarymanagementsystem.repositorylayer.CardRepository;
import com.bvrit.cierclibrarymanagementsystem.servicelayer.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static com.bvrit.cierclibrarymanagementsystem.servicelayer.impl.MailConfigurationServiceImpl.senderEmail;

@Service
public class BookAndUserAuditTrialServiceImpl implements BookAndUserAuditTrialService {
    @Autowired
    private BookAndUserAuditTrialRepository bookAndUserAuditTrialRepository;
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private BookService bookService;
    @Autowired
    private UserService userService;
    @Autowired
    private CardService cardService;
    @Autowired
    private EmailGenerator emailGenerator;
    @Autowired
    private MailConfigurationService mailConfigurationService;


    private static final Integer FINE_PER_DAY = 5;
    private static final Integer MAX_BOOKS_ISSUE_PER_USER=1;



    public String issueBook(String userCode, String bookCode)throws Exception{
        Book book=bookService.findBookByBookCode(bookCode);
        User user=userService.findUserByUserCode(userCode);
        Card card=user.getCard();

        //handled exception if limit of no of books issued per user reaches max limit
        if(card.getBookList().size()==MAX_BOOKS_ISSUE_PER_USER){
            throw new BookCannotBeIssuedException("Book cannot be issued to the user "+user.getUserName()+" as max limit of 1 book per user at a time reached." +
                    "\nReturn the previous book "+card.getBookList().get(0).getName()+" to get the new book issued");
        }


        BookAndUserAuditTrial bookAndUserAuditTrial=BookAndUserAuditTrialTransformer.bookAndUserAuditTrialCreation(book.getReadTime());

        //setting the foreign keys
        bookAndUserAuditTrial.setBookCode(book.getBookCode());
        bookAndUserAuditTrial.setCardCode(card.getCardCode());


        //setting the foreign keys for book and user and bidirectionally mapping them
        card.getBookList().add(book);
        if(card.getStatus().equals(CardStatus.NEW))card.setStatus(CardStatus.ACTIVE);

        book.setCard(card);
        book.setBookStatus(BookStatus.IN_CIRCULATION);

        //saving the child instead of saving both the parents to avoid duplicates
        bookAndUserAuditTrialRepository.save(bookAndUserAuditTrial);

        //sending book issue confirmation mail to the user
        String emailBody=emailGenerator.bookIssueEmailGenerator(user.getUserName(),book.getName(),bookAndUserAuditTrial.getReturnDate());
        mailConfigurationService.mailSender(senderEmail,user.getEmail(),emailBody, "Book Issue Confirmation");

         return "Book "+book.getName()+" is successfully issued to the user "+user.getUserName();
    }
    public String returnBook(String userCode, String bookCode)throws Exception{
        int fineAmount=calculateFineAmount(userCode, bookCode);
        Book book=bookService.findBookByBookCode(bookCode);
        User user=userService.findUserByUserCode(userCode);
        Card card=user.getCard();
        BookAndUserAuditTrial bookAndUserAuditTrial=bookAndUserAuditTrialRepository.findByBookCodeAndCardCodeAndStatus(book.getBookCode(), card.getCardCode(), BookAndUserAuditStatus.ISSUED);

        card.setFineAmount(0);
        card.getBookList().remove(book);

        book.setBookStatus(BookStatus.AVAILABLE);
        book.setCard(null);

        bookAndUserAuditTrial.setReturnedOn(LocalDate.now());
        bookAndUserAuditTrial.setStatus(BookAndUserAuditStatus.RETURNED);

        // saving the child of both the parents....cascading effect
        bookAndUserAuditTrialRepository.save(bookAndUserAuditTrial);

        //sending book issue confirmation mail to the user
        String emailBody=emailGenerator.bookReturnEmailGenerator(user.getUserName(), book.getName(), LocalDate.now());
        mailConfigurationService.mailSender(senderEmail,user.getEmail(),emailBody, "Book Return Confirmation");

        return "Book"+book.getName()+" has been successfully returned to the English Reader's Club by "+user.getUserName();
    }
    @Transactional
    public String sendMailToBookOverdueBorrowers()throws Exception{
        List<BookAndUserAuditStatus>bookAndUserAuditStatusList=new ArrayList<>();
        bookAndUserAuditStatusList.add(BookAndUserAuditStatus.ISSUED);
        bookAndUserAuditStatusList.add(BookAndUserAuditStatus.PENDING);
        List<BookAndUserAuditTrialResponse>bookAndUserAuditTrialResponseList=getBookAndUserAuditTrialListByStatus(bookAndUserAuditStatusList);

        for(BookAndUserAuditTrialResponse bookAndUserAuditTrialResponse: bookAndUserAuditTrialResponseList){
            String userCode=bookAndUserAuditTrialResponse.getCardCode();
            String bookCode=bookAndUserAuditTrialResponse.getBookCode();

            BookAndUserAuditStatus bookAndUserAuditStatus=bookAndUserAuditTrialResponse.getStatus();
            int fineAmount=calculateFineAmount(userCode, bookCode);
            if(fineAmount>0){
                UserResponse userResponse=userService.getUserByUserCode(userCode);
                BookResponse bookResponse=bookService.getBookByBookCode(bookCode);

                bookAndUserAuditTrialRepository.
                        updateBookAndUserAuditTrialStatusByBookCodeAndCardCodeAndStatus(BookAndUserAuditStatus.PENDING,
                                bookCode, userCode, bookAndUserAuditStatus);
                String emailBody=emailGenerator.dueAmountOnBookEmailGenerator(userResponse.getUserName(), bookResponse.getName(), fineAmount);
                mailConfigurationService.mailSender(senderEmail, userResponse.getEmail(), emailBody, "ReturnDate Crossed Remainder");
            }
        }
        return "mails have been sent successfully";
    }
    public int calculateFineAmount(String userCode, String bookCode)throws Exception{
        Book book=bookService.findBookByBookCode(bookCode);
        User user=userService.findUserByUserCode(userCode);
        Card card=user.getCard();


        String receivedCardCode=card.getCardCode();
        String actualCardCode=book.getCard().getCardCode();

        //handled exception when the book is not issued to the particular user
        if(!receivedCardCode.equalsIgnoreCase(actualCardCode)){
            throw new UserBookIssueMismatchException("Book "+book.getName()+"("+bookCode+")"+" is not issued to user "+user.getUserName()+"("+bookCode+")");
        }

        BookAndUserAuditTrial bookAndUserAuditTrial=bookAndUserAuditTrialRepository.findByBookCodeAndCardCodeAndStatus(book.getBookCode(), card.getCardCode(), BookAndUserAuditStatus.ISSUED);

        LocalDate returnDate=bookAndUserAuditTrial.getReturnDate();
        LocalDate presentDate=LocalDate.now();

        int maxLimitDays=book.getReadTime();
        long daysDifference= ChronoUnit.DAYS.between(returnDate, presentDate);

        int fineAmount=0;
        if(daysDifference>maxLimitDays){
            fineAmount = Math.toIntExact(daysDifference  * FINE_PER_DAY);
        }
        card.setFineAmount(fineAmount);

        //saving the card
        cardRepository.save(card);

        return fineAmount;
    }
    public List<BookAndUserAuditTrialResponse> getBookAndUserAuditTrialListByStatus(List<BookAndUserAuditStatus> bookAndUserAuditStatusList){
        List<BookAndUserAuditTrial>bookAndUserAuditTrialList=bookAndUserAuditTrialRepository.findByStatusIn(bookAndUserAuditStatusList);
        List<BookAndUserAuditTrialResponse>bookAndUserAuditTrialResponseList=new ArrayList<>();
        for(BookAndUserAuditTrial bookAndUserAuditTrial: bookAndUserAuditTrialList){
            BookAndUserAuditTrialResponse bookAndUserAuditTrialResponse= BookAndUserAuditTrialTransformer.bookAndUserAuditTrialTransformerToBookAndUserAuditTrialTransformerResponse(bookAndUserAuditTrial);
            bookAndUserAuditTrialResponseList.add(bookAndUserAuditTrialResponse);
        }
        return bookAndUserAuditTrialResponseList;
    }
    public List<UserResponse> getActiveBookBorrowersList()throws Exception{
        List<BookAndUserAuditTrial>bookAndUserAuditTriaList=bookAndUserAuditTrialRepository.findByStatus(BookAndUserAuditStatus.ISSUED);
        List<UserResponse>userResponseList=new ArrayList<>();
        for(BookAndUserAuditTrial bookAndUserAuditTrial: bookAndUserAuditTriaList){
            User user=userService.findUserByUserCode(bookAndUserAuditTrial.getCardCode());
            UserResponse userResponse= UserTransformer.userToUserResponse(user);
            userResponseList.add(userResponse);
        }
        return userResponseList;
    }

}
