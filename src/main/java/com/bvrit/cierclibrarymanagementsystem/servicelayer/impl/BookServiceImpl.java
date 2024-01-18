package com.bvrit.cierclibrarymanagementsystem.servicelayer.impl;

import com.bvrit.cierclibrarymanagementsystem.Transformers.BookTransformer;
import com.bvrit.cierclibrarymanagementsystem.dtos.requestdtos.BookRequest;
import com.bvrit.cierclibrarymanagementsystem.dtos.responsedtos.BookResponse;
import com.bvrit.cierclibrarymanagementsystem.enums.BookAndUserAuditStatus;
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
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookCodeGenerator bookCodeGenerator;
    @Autowired
    private MailConfigurationService mailConfigurationService;
    @Autowired
    private AuthenticationDetailsService authenticationDetailsService;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private GenreService genreService;
    @Autowired
    private AuthorService authorService;


    public List<BookResponse> getFilteredBookResponseList(String bookCode, String authorCode, String name,
                                                          Integer readTime,
                                                          List<GenreEnum>genres, List<BookStatus>statuses,
                                                          Double minRating, Double maxRating,
                                                          Integer minPages, Integer maxPages,
                                                          Integer minPrice, Integer maxPrice){

        List<Book>bookList=getFilteredBookList(bookCode, authorCode, name, readTime,
                genres, statuses, minRating, maxRating, minPages, maxPages, minPrice, maxPrice);

        List<BookResponse>bookResponseList=new ArrayList<>();
        for(Book book:bookList){
            bookResponseList.add(BookTransformer.bookToBookResponse(book));
        }
        return bookResponseList;
    }
    public List<Book> getFilteredBookList(String bookCode, String authorCode, String name,
                                          Integer readTime,
                                          List<GenreEnum>genres, List<BookStatus>statuses,
                                          Double minRating, Double maxRating,
                                          Integer minPages, Integer maxPages,
                                          Integer minPrice, Integer maxPrice){
        List<String>statusList=new ArrayList<>();
        if(statuses != null && !statuses.isEmpty()){
            for(BookStatus bookStatus: statuses){
                statusList.add(bookStatus.toString());
            }
        }else {
            statusList=getBookStatusStringList();
        }
        List<String>genreList=new ArrayList<>();
        if(genres != null && !genres.isEmpty()){
            for(GenreEnum genreEnum: genres){
                genreList.add(genreEnum.toString());
            }
        }else{
            genreList=getGenreStringList();
        }
        List<Book>bookList=bookRepository.findBooksByFilteredBookList(bookCode, authorCode, name, readTime,
                genreList, statusList, minRating, maxRating, minPages, maxPages, minPrice, maxPrice);
        return bookList;
    }
    private List<String> getBookStatusStringList(){
        List<String>statusList=new ArrayList<>();
        for(BookStatus bookStatus: BookStatus.values()){
            statusList.add(bookStatus.toString());
        }
        return statusList;
    }
    private List<String> getGenreStringList(){
        List<String>genreList=new ArrayList<>();
        for(GenreEnum genreEnum: GenreEnum.values()){
            genreList.add(genreEnum.toString());
        }
        return genreList;
    }
    @Transactional
    public String updateBooksStatus(List<String>bookCodeList, BookStatus bookStatus){
        for(String bookCode:bookCodeList){
            bookRepository.updateBookStatusByBookCode(bookStatus, bookCode);
        }
        return "Books status changed successfully";
    }
    public String addBook(BookRequest bookRequest, List<String> authorCodeList, List<GenreEnum>genreEnumList)throws Exception{
        List<Author>authorList=authorService.getAuthorList(authorCodeList);
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

    public String deleteBookList(List<String> bookCodeList) throws Exception {
        List<String>removedBookNameList=new ArrayList<>();
        List<String>notRemovedBookNameList=new ArrayList<>();
        for(String bookCode: bookCodeList) {
            Book book=getFilteredBookList(bookCode, null, null, null, null, null, null, null,null,null,null,null).get(0);;
            if(book.getBookStatus() == BookStatus.AVAILABLE) {

                //removing it's references from authors
                List<Author>authorList=book.getAuthorList();
                for(Author author: authorList)author.getBookList().remove(book);

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
    private List<BookResponse> bookListToBookResponseList(List<Book>bookList){
        List<BookResponse>bookResponseList=new ArrayList<>();
        for(Book book: bookList){
            BookResponse bookResponse=BookTransformer.bookToBookResponse(book);
            bookResponseList.add(bookResponse);
        }
        return bookResponseList;
    }

}
