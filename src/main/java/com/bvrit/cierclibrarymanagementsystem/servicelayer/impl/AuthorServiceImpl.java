package com.bvrit.cierclibrarymanagementsystem.servicelayer.impl;

import com.bvrit.cierclibrarymanagementsystem.dtos.requestdtos.AuthorRequest;
import com.bvrit.cierclibrarymanagementsystem.exceptions.AuthorAlreadyPresentException;
import com.bvrit.cierclibrarymanagementsystem.generators.AuthorCodeGenerator;
import com.bvrit.cierclibrarymanagementsystem.models.Author;
import com.bvrit.cierclibrarymanagementsystem.repositorylayer.AuthorRepository;
import com.bvrit.cierclibrarymanagementsystem.servicelayer.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {
    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private AuthorCodeGenerator authorCodeGenerator;

    public String addAuthor(AuthorRequest authorRequest)throws Exception{
        Optional<Author>optionalAuthor=authorRepository.findByEmail(authorRequest.getEmail());
        if(optionalAuthor.isPresent()){
            throw new AuthorAlreadyPresentException("Author "+authorRequest.getName()+" is already present in the database with author id "+optionalAuthor.get().getId());
        }
        Author author=optionalAuthor.get();

        //setting attribute
        String authorCode= authorCodeGenerator.generate("ATR");
        author.setAuthorCode(authorCode);

        //saving the author to the database
        authorRepository.save(author);
        return "Author "+authorRequest.getName()+" is successfully added to the database";
    }

    public Optional<Author> findAuthorByAuthorCode(String authorCode){
        Optional<Author>optionalAuthor=authorRepository.findByAuthorCode(authorCode);
        return optionalAuthor;
    }
}
