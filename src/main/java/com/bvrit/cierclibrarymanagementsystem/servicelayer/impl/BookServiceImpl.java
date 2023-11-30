package com.bvrit.cierclibrarymanagementsystem.servicelayer.impl;

import com.bvrit.cierclibrarymanagementsystem.Transformers.BookTransformer;
import com.bvrit.cierclibrarymanagementsystem.dtos.requestdtos.BookRequest;
import com.bvrit.cierclibrarymanagementsystem.exceptions.AuthorNotFoundException;
import com.bvrit.cierclibrarymanagementsystem.exceptions.BookAlreadyPresentException;
import com.bvrit.cierclibrarymanagementsystem.models.Author;
import com.bvrit.cierclibrarymanagementsystem.models.Book;
import com.bvrit.cierclibrarymanagementsystem.repositorylayer.AuthorRepository;
import com.bvrit.cierclibrarymanagementsystem.repositorylayer.BookRepository;
import com.bvrit.cierclibrarymanagementsystem.servicelayer.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    BookRepository bookRepository;
    @Autowired
    AuthorRepository authorRepository;
    public String addDetails(BookRequest bookRequest, int authorId)throws Exception{

        Optional<Author>optionalAuthor=authorRepository.findById(authorId);
        if(!optionalAuthor.isPresent()){
            throw new AuthorNotFoundException("Author with "+authorId+" is not present in the database");
        }
        Optional<Book>optionalBook=bookRepository.findByName(bookRequest.getName());
        if(optionalBook.isPresent()){
            throw new BookAlreadyPresentException("Book "+ bookRequest.getName()+" is already present in the database");
        }

        Author author=optionalAuthor.get();
        Book book= BookTransformer.bookRequestToBook(bookRequest);

        //setting the foreign keys
        book.setAuthor(author);

        //bidirectionally mapping
        author.getBookList().add(book);

        //saving the author which automatically saves book
        authorRepository.save(author);

        return "Book "+bookRequest.getName()+" is successfully added to the database";
    }
}
