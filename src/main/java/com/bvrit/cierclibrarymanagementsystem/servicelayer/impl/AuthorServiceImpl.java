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
import com.bvrit.cierclibrarymanagementsystem.servicelayer.BookService;
import com.bvrit.cierclibrarymanagementsystem.servicelayer.TransactionService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
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
    @Autowired
    private BookService bookService;


    public List<AuthorResponse> getFilteredAuthorResponseList(String authorCode, String bookCode, String name, Integer minAge, Integer maxAge,
                                                              Double minRating, Double maxRating, String email){
        List<Author>authorList=authorRepository.authorFilteredList(authorCode, bookCode, name, minAge, maxAge, minRating, maxRating, email);
        List<AuthorResponse>authorResponseList=new ArrayList<>();
        for(Author author: authorList){
            authorResponseList.add(AuthorTransformer.authorToAuthorResponse(author));
        }
        return authorResponseList;
    }
    public List<Author> getAuthorList(List<String>authorCodeList){
        return authorRepository.findByAuthorCodeIn(authorCodeList);
    }

    public String addAuthor(AuthorRequest authorRequest)throws Exception{
        List<Author> authorList=authorRepository.authorFilteredList(null, null, null, null, null, null, null, authorRequest.getEmail());
        if(authorList.size()!=0){
            throw new AuthorAlreadyPresentException("Author "+authorRequest.getName()+" is already present in the database with author id "+authorList.get(0).getId());
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
//    @Transactional(rollbackOn = Exception.class)
    public String deleteAuthor(String authorCode) throws Exception {
        Author author = authorRepository.authorFilteredList(authorCode, null, null, null, null, null, null, null).get(0);
        List<Book> bookList = author.getBookList();
        List<String>bookCodeList=new ArrayList<>();

        for (Book book : bookList) {
            if (book.getBookStatus() != BookStatus.AVAILABLE) {
                throw new AuthorCannotBeRemovedFromDatabaseException("Author " + author.getName() + " cannot be removed from the database because, book " + book.getName() + " is not available." +
                        "\nTo delete the author, status of all books of the author should be AVAILABLE");
            }
            if(book.getAuthorList().size()>1){
                bookCodeList.add(book.getBookCode());
            }
        }
        bookService.deleteBookList(bookCodeList);

        //deleting the author
        authorRepository.deleteById(author.getId());

        //initiating transaction creation
        transactionService.createTransaction(TransactionStatus.AUTHOR_REMOVED, authorCode, "", authenticationDetailsService.getAuthenticationDetails());
        return "Author " + author.getName() + " is removed from the database successfully";
    }

}
