package com.bvrit.cierclibrarymanagementsystem.servicelayer.impl;

import com.bvrit.cierclibrarymanagementsystem.Transformers.AuthorTransformer;
import com.bvrit.cierclibrarymanagementsystem.Transformers.BookTransformer;
import com.bvrit.cierclibrarymanagementsystem.dtos.requestdtos.AuthorRequest;
import com.bvrit.cierclibrarymanagementsystem.dtos.responsedtos.AuthorResponse;
import com.bvrit.cierclibrarymanagementsystem.dtos.responsedtos.BookResponse;
import com.bvrit.cierclibrarymanagementsystem.enums.BookStatus;
import com.bvrit.cierclibrarymanagementsystem.enums.TransactionStatus;
import com.bvrit.cierclibrarymanagementsystem.exceptions.AuthorAlreadyPresentException;
import com.bvrit.cierclibrarymanagementsystem.exceptions.AuthorCannotBeRemovedFromDatabaseException;
import com.bvrit.cierclibrarymanagementsystem.exceptions.AuthorNotFoundException;
import com.bvrit.cierclibrarymanagementsystem.generators.AuthorCodeGenerator;
import com.bvrit.cierclibrarymanagementsystem.models.Author;
import com.bvrit.cierclibrarymanagementsystem.models.Book;
import com.bvrit.cierclibrarymanagementsystem.repositorylayer.AuthorRepository;
import com.bvrit.cierclibrarymanagementsystem.servicelayer.AuthenticationDetailsService;
import com.bvrit.cierclibrarymanagementsystem.servicelayer.AuthorService;
import com.bvrit.cierclibrarymanagementsystem.servicelayer.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private AuthorCodeGenerator authorCodeGenerator;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private AuthenticationDetailsService authenticationDetailsService;

    public String addAuthor(AuthorRequest authorRequest)throws Exception{
        Optional<Author>optionalAuthor=authorRepository.findAuthorByEmail(authorRequest.getEmail());
        if(optionalAuthor.isPresent()){
            throw new AuthorAlreadyPresentException("Author "+authorRequest.getName()+" is already present in the database with author id "+optionalAuthor.get().getId());
        }
        Author author= AuthorTransformer.authorRequestToAuthor(authorRequest);

        //setting attribute
        String authorCode= authorCodeGenerator.generate("ATR");
        author.setAuthorCode(authorCode);

        //saving the author to the database
        Author savedAuthor=authorRepository.save(author);

        //initiating transaction creation
        transactionService.createTransaction(TransactionStatus.AUTHOR_ADDED, savedAuthor.getAuthorCode(), "", authenticationDetailsService.getAuthenticationDetails());
        return "Author "+authorRequest.getName()+" is successfully added to the database";
    }
    public String deleteAuthorByAuthorCode(String authorCode) throws Exception{
        Author author=getAuthorEntityByAuthorCode(authorCode);
        List<Book>bookList=author.getBookList();

        for(Book book: bookList){
            if(book.getBookStatus()!= BookStatus.AVAILABLE){
                throw new AuthorCannotBeRemovedFromDatabaseException("Author "+author.getName()+" cannot be removed from the database because, book "+book.getName()+" is not available." +
                        "\nTo delete the author, status of all books of the author should be AVAILABLE");
            }
        }
        authorRepository.deleteById(author.getId());

        //initiating transaction creation
        transactionService.createTransaction(TransactionStatus.AUTHOR_REMOVED, authorCode, "", authenticationDetailsService.getAuthenticationDetails());
        return "Author "+author.getName()+" is removed from the database successfully";
    }


    //searching authors (or) finding authors with author code and author mail id which are unique
    public AuthorResponse findAuthorByAuthorCode(String authorCode) throws AuthorNotFoundException {
        Optional<Author>optionalAuthor=authorRepository.findAuthorByAuthorCode(authorCode);
        if(!optionalAuthor.isPresent()){
            throw new AuthorNotFoundException("Author not found with the particular author code: "+authorCode+". Try again with the valid code");
        }
        Author author=optionalAuthor.get();
        AuthorResponse authorResponse=AuthorTransformer.authorToAuthorResponse(author);
        return authorResponse;
    }
    public AuthorResponse findAuthorByAuthorEmail(String email) throws AuthorNotFoundException {
        Optional<Author>optionalAuthor=authorRepository.findAuthorByEmail(email);
        if(!optionalAuthor.isPresent()){
            throw new AuthorNotFoundException("Author not found with the particular author email: "+email+". Try again with the valid email");
        }
        Author author=optionalAuthor.get();
        AuthorResponse authorResponse=AuthorTransformer.authorToAuthorResponse(author);
        return authorResponse;
    }
    public List<BookResponse> getBookListByAuthorCode(String authorCode)  {
        Author author=authorRepository.findAuthorByAuthorCode(authorCode).get();
        List<Book>bookList=author.getBookList();
        List<BookResponse>bookResponseList=new ArrayList<>();
        for(Book book: bookList){
            BookResponse bookResponse= BookTransformer.bookToBookResponse(book);
            bookResponseList.add(bookResponse);
        }
        return bookResponseList;
    }

    //below method or function is used for internal purpose
    private Author getAuthorEntityByAuthorCode(String authorCode) throws AuthorNotFoundException {
        Optional<Author>optionalAuthor=authorRepository.findAuthorByAuthorCode(authorCode);
        if(!optionalAuthor.isPresent()){
            throw new AuthorNotFoundException("Author not found with the particular author code: "+authorCode+". Try again with the valid code");
        }
        return optionalAuthor.get();
    }

}
