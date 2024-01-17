package com.bvrit.cierclibrarymanagementsystem.servicelayer.impl;

import com.bvrit.cierclibrarymanagementsystem.Transformers.BookAndUserAuditTrialTransformer;
import com.bvrit.cierclibrarymanagementsystem.Transformers.UserTransformer;
import com.bvrit.cierclibrarymanagementsystem.dtos.responsedtos.BookAndUserAuditTrialResponse;
import com.bvrit.cierclibrarymanagementsystem.dtos.responsedtos.BookResponse;
import com.bvrit.cierclibrarymanagementsystem.dtos.responsedtos.UserResponse;
import com.bvrit.cierclibrarymanagementsystem.enums.*;
import com.bvrit.cierclibrarymanagementsystem.exceptions.*;
import com.bvrit.cierclibrarymanagementsystem.generators.BookAndUserAuditTrialCodeGenerator;
import com.bvrit.cierclibrarymanagementsystem.generators.EmailGenerator;
import com.bvrit.cierclibrarymanagementsystem.models.Book;
import com.bvrit.cierclibrarymanagementsystem.models.BookAndUserAuditTrial;
import com.bvrit.cierclibrarymanagementsystem.models.Card;
import com.bvrit.cierclibrarymanagementsystem.models.User;
import com.bvrit.cierclibrarymanagementsystem.repositorylayer.BookAndUserAuditTrialRepository;
import com.bvrit.cierclibrarymanagementsystem.repositorylayer.CardRepository;
import com.bvrit.cierclibrarymanagementsystem.servicelayer.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.bvrit.cierclibrarymanagementsystem.servicelayer.impl.MailConfigurationServiceImpl.SENDER_EMAIL;

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
    private TransactionService transactionService;
    @Autowired
    private EmailGenerator emailGenerator;
    @Autowired
    private BookAndUserAuditTrialCodeGenerator bookAndUserAuditTrialCodeGenerator;
    @Autowired
    private MailConfigurationService mailConfigurationService;
    @Autowired
    private AuthenticationDetailsService authenticationDetailsService;

    private static Integer FINE_PER_DAY =5;
    private static Integer MAX_BOOKS_ISSUE_PER_USER=1;

    public void updateFinePerDayAndMaxBooksIssuePerUser(int finePerDay, int maxBooksIssuePerUser){
        FINE_PER_DAY=finePerDay;
        MAX_BOOKS_ISSUE_PER_USER=maxBooksIssuePerUser;
    }

    public List<BookAndUserAuditTrialResponse> getAuditTrialFilteredList(String issueReturnCode, String userCode, String bookCode,
                                                                         List<BookAndUserAuditStatus> statuses,
                                                                         List<ReturnItem> returnItems,
                                                                         LocalDate issueDateStart, LocalDate issueDateEnd,
                                                                         LocalDate returnDateStart, LocalDate returnDateEnd,
                                                                         LocalDate returnedOnStart, LocalDate returnedOnEnd,
                                                                         Boolean returnedOnIsNull){



        List<String>statusList=new ArrayList<>();
        if(!statuses.isEmpty()){
            for(BookAndUserAuditStatus bookAndUserAuditStatus: statuses){
                statusList.add(bookAndUserAuditStatus.toString());
            }
        }else {
            statusList=getBookAndUserAuditStatusStringList();
        }

        List<String>returnItemList=new ArrayList<>();
        if(!returnItems.isEmpty()){
            for(ReturnItem returnItem: returnItems){
                returnItemList.add(returnItem.toString());
            }
        }else{
            returnItemList=getReturnItemStringList();
        }

        List<BookAndUserAuditTrial>bookAndUserAuditTrialList=
                bookAndUserAuditTrialRepository.bookAndUserAuditTrialFilteredList(issueReturnCode, userCode, bookCode, statusList, returnItemList,
                        issueDateStart, issueDateEnd, returnDateStart, returnDateEnd,
                        returnedOnStart, returnedOnEnd, returnedOnIsNull);

        List<BookAndUserAuditTrialResponse>bookAndUserAuditTrialResponseList=new ArrayList<>();
        for(BookAndUserAuditTrial bookAndUserAuditTrial: bookAndUserAuditTrialList){
            bookAndUserAuditTrialResponseList.add(BookAndUserAuditTrialTransformer
                    .bookAndUserAuditTrialToBookAndUserAuditTrialResponse(bookAndUserAuditTrial));
        }
        return bookAndUserAuditTrialResponseList;
    }

    public String issueBook(String userCode, String bookCode)throws Exception{
        Book book=bookService.getFilteredBookList(bookCode, null, null, null, null, null, null, null,null,null,null,null).get(0);
        User user=userService.findUserByUserCode(userCode);
        Card card=user.getCard();

        //handled exception if limit of no of books issued per user reaches max limit
        if(card.getBookList().size()==MAX_BOOKS_ISSUE_PER_USER){
            throw new BookCannotBeIssuedException("Book cannot be issued to the user "+user.getUserName()+" as max limit of 1 book per user at a time reached." +
                    "\nReturn the previous book "+card.getBookList().get(0).getName()+" to get the new book issued");
        }

        //handled a case with exception when card status is frozen, blocked or expired
        if(card.getStatus()==CardStatus.FREEZE || card.getStatus()==CardStatus.BLOCKED || card.getStatus()==CardStatus.EXPIRED){
            throw new BookCannotBeIssuedException("Cannot issue the book to the user "+user.getUserName()+" because user card status is "+card.getStatus()+" with comment "+card.getComment());
        }

        BookAndUserAuditTrial bookAndUserAuditTrial=BookAndUserAuditTrialTransformer.bookAndUserAuditTrialCreation(book.getReadTime());

        //generating the unique codes and setting the attributes
        String code=bookAndUserAuditTrialCodeGenerator.generate("IR");
        bookAndUserAuditTrial.setIssueReturnCode(code);
        bookAndUserAuditTrial.setReturnItem(ReturnItem.NULL);
        card.setNumberOfBooksIssued(card.getNumberOfBooksIssued()+1);

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

        //initiating transaction creation
        transactionService.createTransaction(TransactionStatus.ISSUED, bookCode, userCode, authenticationDetailsService.getAuthenticationDetails());

        //sending book issue confirmation mail to the user
        String emailBody=emailGenerator.bookIssueEmailGenerator(user.getUserName(),book.getName(),bookAndUserAuditTrial.getReturnDate());
        mailConfigurationService.mailSender(SENDER_EMAIL,user.getEmail(),emailBody, "Book Issue Confirmation");

         return "Book "+book.getName()+" is successfully issued to the user "+user.getUserName();
    }
    public String returnBook(String issueReturnCode, ReturnItem returnItem, GeneralComments comment)throws Exception{

        List<String>statusList=getBookAndUserAuditStatusStringList();
        List<String>returnItemList=getReturnItemStringList();

        List<BookAndUserAuditTrial> bookAndUserAuditTrialList= bookAndUserAuditTrialRepository.bookAndUserAuditTrialFilteredList
                (issueReturnCode, null, null, statusList,returnItemList,null,null,
                                null,null,null,null,null);
        if(bookAndUserAuditTrialList.size()==0){
            throw new BookAndUserAuditTrialNotFoundException("Invalid Book And User Audit Trial Code");
        }

        //getting the attributes
        BookAndUserAuditTrial bookAndUserAuditTrial=bookAndUserAuditTrialList.get(0);

        BookAndUserAuditStatus status=bookAndUserAuditTrial.getStatus();
        String userCode=bookAndUserAuditTrial.getCardCode();
        String bookCode=bookAndUserAuditTrial.getBookCode();

        if(status==BookAndUserAuditStatus.RETURNED ||status==BookAndUserAuditStatus.FAILURE){
            throw new BookAlreadyReturnedException("Book cannot be returned as it is already "+status.toString());
        }

        //generally calculating the final fine amount
        calculateFineAmount(issueReturnCode, 0);

        Book book=bookService.getFilteredBookList(bookCode, null, null, null, null, null, null, null,null,null,null,null).get(0);;
        User user=userService.findUserByUserCode(userCode);
        Card card=user.getCard();

        //handling user card
        if(card.getStatus()==CardStatus.FREEZE)card.setStatus(CardStatus.ACTIVE);
        if(card.getComment()!=null)card.setComment("");
        card.setFineAmount(0);
        card.getBookList().remove(book);

        //handling book
        switch(returnItem){
            case ORIGINAL_BOOK -> book.setBookStatus(BookStatus.AVAILABLE);
            case NEW_BOOK, DAMAGED_BOOK_AND_MONEY, MONEY -> {
                book.setBookStatus(BookStatus.NOT_AVAILABLE);
                book.setComment(comment.getDisplayName());
            }
        }
        book.setCard(null);

        //handling issue-return transaction
        bookAndUserAuditTrial.setReturnedOn(LocalDate.now());
        bookAndUserAuditTrial.setStatus(BookAndUserAuditStatus.RETURNED);
        bookAndUserAuditTrial.setReturnItem(returnItem);

        // saving the child of both the parents....cascading effect
        bookAndUserAuditTrialRepository.save(bookAndUserAuditTrial);

        //initiating transaction creation
        transactionService.createTransaction(TransactionStatus.RETURNED, bookCode, userCode, authenticationDetailsService.getAuthenticationDetails());

        //sending book return confirmation mail to the user
        String emailBody = emailGenerator.bookReturnEmailGenerator(user.getUserName(), book.getName(), LocalDate.now());
        mailConfigurationService.mailSender(SENDER_EMAIL, user.getEmail(), emailBody, "Book Return Confirmation");

        return "Book" + book.getName() + " has been successfully returned to the English Reader's Club by " + user.getUserName();
    }


    @Transactional
    @Scheduled(cron = "0 0 10 ? * *")
    public String sendMailToBookOverdueBorrowers()throws Exception{
        List<String>statusList=new ArrayList<>(Arrays.asList("ISSUED", "PENDING", "FREEZE"));
        List<String>returnItemList=getReturnItemStringList();

        //calling the other method to get the list based on multiple status (issue and pending)
        List<BookAndUserAuditTrial> bookAndUserAuditTrialList= bookAndUserAuditTrialRepository.bookAndUserAuditTrialFilteredList
                (null, null, null, statusList,returnItemList,null,null,
                        null,null,null,null,null);

        for(BookAndUserAuditTrial bookAndUserAuditTrial: bookAndUserAuditTrialList){
            String userCode=bookAndUserAuditTrial.getCardCode();
            String bookCode=bookAndUserAuditTrial.getBookCode();
            String issueReturnCode=bookAndUserAuditTrial.getIssueReturnCode();
            BookAndUserAuditStatus newStatus=BookAndUserAuditStatus.PENDING;

            BookAndUserAuditStatus bookAndUserAuditStatus=bookAndUserAuditTrial.getStatus();
            int fineAmount=calculateFineAmount(issueReturnCode, 0);
            if(fineAmount>0){
                UserResponse userResponse=userService.getUserByUserCode(userCode);
                BookResponse bookResponse=bookService.getFilteredBookResponseList(bookCode, null, null, null, null, null, null, null,null,null,null,null).get(0);

                bookAndUserAuditTrialRepository.updateBookAndUserAuditTrialStatusByIssueReturnCodeAndStatus(issueReturnCode, newStatus.toString());

                String emailBody=emailGenerator.dueAmountOnBookEmailGenerator(userResponse.getUserName(), bookResponse.getName(), fineAmount);
                mailConfigurationService.mailSender(SENDER_EMAIL, userResponse.getEmail(), emailBody, "ReturnDate Crossed Remainder");
            }
        }
        return "mails have been sent successfully";
    }
    public int calculateFineAmount(String issueReturnCode, int additionalAmount)throws Exception{
        List<String>statusList=getBookAndUserAuditStatusStringList();
        List<String>returnItemList=getReturnItemStringList();

        List<BookAndUserAuditTrial> bookAndUserAuditTrialList= bookAndUserAuditTrialRepository.bookAndUserAuditTrialFilteredList
                (issueReturnCode, null, null, statusList,returnItemList,null,null,
                        null,null,null,null,null);
        if(bookAndUserAuditTrialList.size()==0){
            throw new BookAndUserAuditTrialNotFoundException("Invalid Book And User Audit Trial Code");
        }

        //getting the attributes
        BookAndUserAuditTrial bookAndUserAuditTrial=bookAndUserAuditTrialList.get(0);
        String cardCode=bookAndUserAuditTrial.getCardCode();
        String bookCode=bookAndUserAuditTrial.getBookCode();

        //finding book and user from the attributes
        Book book=bookService.getFilteredBookList(bookCode, null, null, null, null, null, null, null,null,null,null,null).get(0);;
        User user=userService.findUserByUserCode(cardCode);
        Card card=user.getCard();

        if(card.getStatus()==CardStatus.FREEZE || card.getStatus()==CardStatus.BLOCKED || card.getStatus()==CardStatus.EXPIRED){
            return card.getFineAmount();
        }

        LocalDate returnDate=bookAndUserAuditTrial.getReturnDate();
        LocalDate presentDate=LocalDate.now();

        int maxLimitDays=book.getReadTime();
        long daysDifference= ChronoUnit.DAYS.between(returnDate, presentDate);

        int fineAmount=0;
        if(daysDifference>maxLimitDays){
            fineAmount = Math.toIntExact(daysDifference  * FINE_PER_DAY);
        }
        fineAmount+=additionalAmount;
        card.setFineAmount(fineAmount);

        //saving the card
        cardRepository.save(card);

        return fineAmount;
    }

    public String updateStatusByBookAndUserCodeAndStatus(String issueReturnCode, BookAndUserAuditStatus newStatus)throws Exception{
        List<String>statusList=getBookAndUserAuditStatusStringList();
        List<String>returnItemList=getReturnItemStringList();

        List<BookAndUserAuditTrial> bookAndUserAuditTrialList= bookAndUserAuditTrialRepository.bookAndUserAuditTrialFilteredList
                (issueReturnCode, null, null, statusList,returnItemList,null,null,
                        null,null,null,null,null);
        if(bookAndUserAuditTrialList.size()==0){
            throw new BookAndUserAuditTrialNotFoundException("Invalid Book And User Audit Trial Code");
        }

        BookAndUserAuditTrial bookAndUserAuditTrial=bookAndUserAuditTrialList.get(0);
        bookAndUserAuditTrialRepository.updateBookAndUserAuditTrialStatusByIssueReturnCodeAndStatus(bookAndUserAuditTrial.getIssueReturnCode(), newStatus.toString());
        return "updated Successfully";
    }
    public List<UserResponse> getActiveBookBorrowersList()throws Exception{

        List<String>statusList=new ArrayList<>(Arrays.asList("ISSUED", "PENDING", "FREEZE"));
        List<String>returnItemList=getReturnItemStringList();

        //calling the other method to get the list based on the status
        List<BookAndUserAuditTrial> bookAndUserAuditTrialList= bookAndUserAuditTrialRepository.bookAndUserAuditTrialFilteredList
                (null, null, null, statusList,returnItemList,null,null,
                        null,null,null,null,null);
        if(bookAndUserAuditTrialList.size()==0){
            throw new BookAndUserAuditTrialNotFoundException("Invalid Book And User Audit Trial Code");
        }
        List<UserResponse>userResponseList=new ArrayList<>();
        for(BookAndUserAuditTrial bookAndUserAuditTrial: bookAndUserAuditTrialList){
            User user=userService.findUserByUserCode(bookAndUserAuditTrial.getCardCode());
            UserResponse userResponse= UserTransformer.userToUserResponse(user);
            userResponseList.add(userResponse);
        }
        return userResponseList;
    }
    @Transactional(rollbackFor = Exception.class)
    public String freezeOrUnFreeze(String issueReturnCode, GeneralComments comment, CardStatus newCardStatus, BookAndUserAuditStatus newStatus)throws Exception{
        List<String>statusList=getBookAndUserAuditStatusStringList();
        List<String>returnItemList=getReturnItemStringList();
        List<BookAndUserAuditTrial> bookAndUserAuditTrialList= bookAndUserAuditTrialRepository.bookAndUserAuditTrialFilteredList
                (issueReturnCode, null, null, statusList,returnItemList,null,null,
                        null,null,null,null,null);
        if(bookAndUserAuditTrialList.size()==0){
            throw new BookAndUserAuditTrialNotFoundException("Invalid Book And User Audit Trial Code");
        }
        BookAndUserAuditTrial bookAndUserAuditTrial=bookAndUserAuditTrialList.get(0);
        String userCode=bookAndUserAuditTrial.getCardCode();
        User user=userService.findUserByUserCode(userCode);
        Card card=user.getCard();
        CardStatus cardStatus=card.getStatus();

        if(newStatus==bookAndUserAuditTrial.getStatus()){
            throw new FreezeOrUnFreezeException("User and Book and User Audit Trial cannot be "+
                    bookAndUserAuditTrial.getStatus()+" as it is already in the same state");
        }

        if(newCardStatus==CardStatus.FREEZE)calculateFineAmount(issueReturnCode, 0);

        //freezing the card and mentioning the reason in the comment section of card
        card.setStatus(newCardStatus);
        card.setComment(comment.getDisplayName());

        //freezing the transaction to eliminate the automatic calculation of fine amount and sending the emails until few days
        bookAndUserAuditTrial.setStatus(newStatus);

        if(newStatus==BookAndUserAuditStatus.FREEZE)return "user "+user.getUserName()+" and respective issue return process has been frozen as "+comment.getDisplayName();
        else return "user "+user.getUserName()+" and respective issue return process has been unfrozen";
    }
    private List<String> getBookAndUserAuditStatusStringList(){
        List<String>statusList=new ArrayList<>();
        for(BookAndUserAuditStatus bookAndUserAuditStatus: BookAndUserAuditStatus.values()){
            statusList.add(bookAndUserAuditStatus.toString());
        }
        return statusList;
    }
    private List<String> getReturnItemStringList(){
        List<String>returnItemList=new ArrayList<>();
        for(ReturnItem returnItem: ReturnItem.values()){
            returnItemList.add(returnItem.toString());
        }
        return returnItemList;
    }

}
