package com.bvrit.cierclibrarymanagementsystem.servicelayer.impl;

import com.bvrit.cierclibrarymanagementsystem.Transformers.BookTransformer;
import com.bvrit.cierclibrarymanagementsystem.dtos.requestdtos.BookRequest;
import com.bvrit.cierclibrarymanagementsystem.dtos.responsedtos.BookResponse;
import com.bvrit.cierclibrarymanagementsystem.enums.BookStatus;
import com.bvrit.cierclibrarymanagementsystem.enums.GenreEnum;
import com.bvrit.cierclibrarymanagementsystem.enums.TransactionStatus;
import com.bvrit.cierclibrarymanagementsystem.exceptions.AuthorNotFoundException;
import com.bvrit.cierclibrarymanagementsystem.exceptions.BookCannotBeRemovedFromDatabaseException;
import com.bvrit.cierclibrarymanagementsystem.exceptions.BookNotFoundException;
import com.bvrit.cierclibrarymanagementsystem.generators.BookCodeGenerator;
import com.bvrit.cierclibrarymanagementsystem.models.Author;
import com.bvrit.cierclibrarymanagementsystem.models.Book;
import com.bvrit.cierclibrarymanagementsystem.models.Genre;
import com.bvrit.cierclibrarymanagementsystem.repositorylayer.AuthorRepository;
import com.bvrit.cierclibrarymanagementsystem.repositorylayer.BookRepository;
import com.bvrit.cierclibrarymanagementsystem.servicelayer.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private BookCodeGenerator bookCodeGenerator;
    @Autowired
    private MailConfigurationService mailConfigurationService;
    @Autowired
    private AuthenticationDetailsService authenticationDetailsService;

    @Autowired
    private GenreService genreService;

    public String addBook(BookRequest bookRequest, List<String> authorCodeList, List<GenreEnum>genreEnumList)throws Exception{
        List<Author>authorList=authorRepository.findByAuthorCodeIn(authorCodeList);
        if(authorCodeList.size()==0|| authorCodeList.size()!=authorList.size()){
            throw new AuthorNotFoundException("Author(s) with "+authorCodeList+" is/are not present in the database");
        }

        Book book= BookTransformer.bookRequestToBook(bookRequest);

        //setting attribute
        String bookCode= bookCodeGenerator.generate("BK");
        book.setBookCode(bookCode);

        //setting the foreign keys
        List<Genre>genreList=genreService.getGenreList(genreEnumList);
        book.setGenreList(genreList);
        book.setAuthorList(authorList);

        //bidirectionally mapping
        for(Author author: authorList)author.getBookList().add(book);
        for(Genre genre: genreList)genre.getBookList().add(book);

        //saving the author which automatically saves book
        bookRepository.save(book);

        //initiating transaction creation
        transactionService.createTransaction(TransactionStatus.BOOK_ADDED, bookCode, "", authenticationDetailsService.getAuthenticationDetails());
        return "Book "+bookRequest.getName()+" is successfully added to the database";
    }
    @Transactional
    public String updateBooksStatus(List<String>bookCodeList, BookStatus bookStatus){
        for(String bookCode:bookCodeList){
            bookRepository.updateBookStatusByBookCode(bookStatus, bookCode);
        }
        return "Books status changed successfully";
    }

    public BookResponse getBookByBookCode(String bookCode) throws BookNotFoundException {
        Optional<Book>optionalBook=bookRepository.findBookByBookCode(bookCode);
        if(!optionalBook.isPresent()){
            throw new BookNotFoundException("Book with the particular Book code "+bookCode+" is not present in the database");
        }
        Book book=optionalBook.get();
        BookResponse bookResponse=BookTransformer.bookToBookResponse(book);
        return bookResponse;
    }
    public List<BookResponse> getBookListByBookName(String bookName){
        List<Book>bookList=bookRepository.findBookByName(bookName);
        List<BookResponse>bookResponseList=new ArrayList<>();
        for(Book book: bookList){
            BookResponse bookResponse=BookTransformer.bookToBookResponse(book);
            bookResponseList.add(bookResponse);
        }
        return bookResponseList;
    }

    public List<BookResponse> getBookListByGenre(GenreEnum genreEnum){
        List<GenreEnum>genreEnumList=new ArrayList<>(Arrays.asList(genreEnum));
        List<Genre>genreList=genreService.getGenreList(genreEnumList);
        List<Book>bookList=bookRepository.findBookListByGenreListIn(genreList);
        return bookListToBookResponseList(bookList);
    }
    public List<BookResponse> getBookListByBookStatus(BookStatus bookStatus){
        List<Book>bookList=bookRepository.findBookListByBookStatus(bookStatus);
        return bookListToBookResponseList(bookList);
    }

    public String deleteBookByBookCode(List<String> bookCodeList) throws Exception {
        List<String>removedBookNameList=new ArrayList<>();
        List<String>notRemovedBookNameList=new ArrayList<>();
        for(String bookCode: bookCodeList) {
            Book book = findBookByBookCode(bookCode);
            if(book.getBookStatus() == BookStatus.AVAILABLE) {


                bookRepository.deleteById(book.getId());

                //initiating transaction creation
                transactionService.createTransaction(TransactionStatus.BOOK_REMOVED, bookCode, "", authenticationDetailsService.getAuthenticationDetails());

                removedBookNameList.add(book.getName());
            }
            else notRemovedBookNameList.add(book.getName());
        }
        if(notRemovedBookNameList.size()==0){
            return "Books "+removedBookNameList+" removed successfully";
        }
        throw new BookCannotBeRemovedFromDatabaseException("Book/s "+notRemovedBookNameList+" cannot be removed from database." +
                "\nTo remove the book it's status should be null");
    }

    //below methods for internal purpose....not to call through API
    public Book findBookByBookCode(String bookCode)throws Exception{
        Optional<Book>optionalBook=bookRepository.findBookByBookCode(bookCode);
        if(!optionalBook.isPresent()){
            throw new BookNotFoundException("Book with the particular Book code "+bookCode+" is not present in the database");
        }
        return optionalBook.get();
    }

    private List<BookResponse> bookListToBookResponseList(List<Book>bookList){
        List<BookResponse>bookResponseList=new ArrayList<>();
        for(Book book: bookList){
            BookResponse bookResponse=BookTransformer.bookToBookResponse(book);
            bookResponseList.add(bookResponse);
        }
        return bookResponseList;
    }

}
