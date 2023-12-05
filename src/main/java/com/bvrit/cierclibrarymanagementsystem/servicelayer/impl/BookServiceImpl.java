package com.bvrit.cierclibrarymanagementsystem.servicelayer.impl;

import com.bvrit.cierclibrarymanagementsystem.Transformers.BookTransformer;
import com.bvrit.cierclibrarymanagementsystem.dtos.requestdtos.BookRequest;
import com.bvrit.cierclibrarymanagementsystem.dtos.responsedtos.BookResponse;
import com.bvrit.cierclibrarymanagementsystem.exceptions.AuthorNotFoundException;
import com.bvrit.cierclibrarymanagementsystem.exceptions.BookNotFoundException;
import com.bvrit.cierclibrarymanagementsystem.generators.BookCodeGenerator;
import com.bvrit.cierclibrarymanagementsystem.models.Author;
import com.bvrit.cierclibrarymanagementsystem.models.Book;
import com.bvrit.cierclibrarymanagementsystem.repositorylayer.AuthorRepository;
import com.bvrit.cierclibrarymanagementsystem.repositorylayer.BookRepository;
import com.bvrit.cierclibrarymanagementsystem.servicelayer.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookCodeGenerator bookCodeGenerator;

    public String addBook(BookRequest bookRequest, String authorCode)throws Exception{

        Optional<Author>optionalAuthor=authorRepository.findAuthorByAuthorCode(authorCode);
        if(!optionalAuthor.isPresent()){
            throw new AuthorNotFoundException("Author with "+authorCode+" is not present in the database");
        }

        Author author=optionalAuthor.get();
        Book book= BookTransformer.bookRequestToBook(bookRequest);

        //setting attribute
        String bookCode= bookCodeGenerator.generate("BK");
        book.setBookCode(bookCode);

        //setting the foreign keys
        book.setAuthor(author);

        //bidirectionally mapping
        author.getBookList().add(book);

        //saving the author which automatically saves book
        authorRepository.save(author);

        return "Book "+bookRequest.getName()+" is successfully added to the database";
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
        List<Book>bookList=bookRepository.findByName(bookName);
        List<BookResponse>bookResponseList=new ArrayList<>();
        for(Book book: bookList){
            BookResponse bookResponse=BookTransformer.bookToBookResponse(book);
            bookResponseList.add(bookResponse);
        }
        return bookResponseList;
    }

    public Book findBookByBookCode(String bookCode)throws Exception{
        Optional<Book>optionalBook=bookRepository.findBookByBookCode(bookCode);
        if(!optionalBook.isPresent()){
            throw new BookNotFoundException("Book with the particular Book code "+bookCode+" is not present in the database");
        }
        return optionalBook.get();
    }
}
