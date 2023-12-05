package com.bvrit.cierclibrarymanagementsystem.servicelayer.impl;

import com.bvrit.cierclibrarymanagementsystem.enums.BookAndUserAuditStatus;
import com.bvrit.cierclibrarymanagementsystem.enums.BookStatus;
import com.bvrit.cierclibrarymanagementsystem.enums.CardStatus;
import com.bvrit.cierclibrarymanagementsystem.exceptions.UserBookIssueMismatchException;
import com.bvrit.cierclibrarymanagementsystem.models.Book;
import com.bvrit.cierclibrarymanagementsystem.models.BookAndUserAuditTrial;
import com.bvrit.cierclibrarymanagementsystem.models.Card;
import com.bvrit.cierclibrarymanagementsystem.models.User;
import com.bvrit.cierclibrarymanagementsystem.repositorylayer.BookAndUserAuditTrialRepository;
import com.bvrit.cierclibrarymanagementsystem.repositorylayer.CardRepository;
import com.bvrit.cierclibrarymanagementsystem.servicelayer.BookAndUserAuditTrialService;
import com.bvrit.cierclibrarymanagementsystem.servicelayer.BookService;
import com.bvrit.cierclibrarymanagementsystem.servicelayer.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.concurrent.TimeUnit;

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

    private static final Integer FINE_PER_DAY = 5;

    public String issueBook(String userCode, String bookCode)throws Exception{
        Book book=bookService.findBookByBookCode(bookCode);
        User user=userService.findUserByUserCode(userCode);
        Card card=user.getCard();


        BookAndUserAuditTrial bookAndUserAuditTrial=new BookAndUserAuditTrial();

        //setting the attribute
        bookAndUserAuditTrial.setStatus(BookAndUserAuditStatus.ISSUED);

        //setting the foreign keys
        bookAndUserAuditTrial.setBook(book);
        bookAndUserAuditTrial.setCard(card);

        //bidirectionally mapping
        book.getBookAndUserAuditTrialList().add(bookAndUserAuditTrial);
        card.getBookAndUserAuditTrialList().add(bookAndUserAuditTrial);

        //setting the foreign keys for book and user and bidirectionally mapping them
        card.getBookList().add(book);
        if(card.getStatus().equals(CardStatus.NEW))card.setStatus(CardStatus.ACTIVE);

        book.setCard(card);
        book.setBookStatus(BookStatus.NOT_AVAILABLE);

        //saving the child instead of saving both the parents to avoid duplicates
         bookAndUserAuditTrialRepository.save(bookAndUserAuditTrial);

         return "Book "+book.getName()+" is successfully issued to the user "+user.getUserName();
    }
    public String returnBook(String userCode, String bookCode)throws Exception{
        int fineAmount=calculateFineAmount(userCode, bookCode);
        Book book=bookService.findBookByBookCode(bookCode);
        User user=userService.findUserByUserCode(userCode);
        Card card=user.getCard();
        BookAndUserAuditTrial bookAndUserAuditTrial=bookAndUserAuditTrialRepository.findByBookAndCardAndStatus(book, card, BookAndUserAuditStatus.ISSUED);

        card.setFineAmount(0);
        card.getBookList().remove(book);

        book.setBookStatus(BookStatus.AVAILABLE);
        book.setCard(null);

        bookAndUserAuditTrial.setReturnDate(LocalDate.now());
        bookAndUserAuditTrial.setStatus(BookAndUserAuditStatus.RETURNED);

        // saving the child of both the parents....cascading effect
        bookAndUserAuditTrialRepository.save(bookAndUserAuditTrial);

        return "Book"+book.getName()+" has been successfully returned to the English Reader's Club by "+user.getUserName();
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

        BookAndUserAuditTrial bookAndUserAuditTrial=bookAndUserAuditTrialRepository.findByBookAndCardAndStatus(book, card, BookAndUserAuditStatus.ISSUED);
        Date issueDate=bookAndUserAuditTrial.getIssueDate();
        int maxLimitDays=book.getReadTime();

        long milliSeconds = Math.abs(System.currentTimeMillis()-issueDate.getTime());
        Long days = TimeUnit.DAYS.convert(milliSeconds,TimeUnit.MILLISECONDS);

        int fineAmount=0;
        if(days>maxLimitDays){
            fineAmount = Math.toIntExact((days - maxLimitDays) * FINE_PER_DAY);
        }
        card.setFineAmount(fineAmount);

        //saving the card
        cardRepository.save(card);

        return fineAmount;
    }
}
