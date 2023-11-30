package com.bvrit.cierclibrarymanagementsystem.servicelayer.impl;

import com.bvrit.cierclibrarymanagementsystem.Transformers.UserTransformer;
import com.bvrit.cierclibrarymanagementsystem.dtos.requestdtos.AuthorRequest;
import com.bvrit.cierclibrarymanagementsystem.dtos.requestdtos.UserRequest;
import com.bvrit.cierclibrarymanagementsystem.exceptions.AuthorAlreadyPresentException;
import com.bvrit.cierclibrarymanagementsystem.models.Author;
import com.bvrit.cierclibrarymanagementsystem.models.User;
import com.bvrit.cierclibrarymanagementsystem.repositorylayer.AuthorRepository;
import com.bvrit.cierclibrarymanagementsystem.servicelayer.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {
    @Autowired
    private AuthorRepository authorRepository;

    public String addDetails(AuthorRequest authorRequest)throws Exception{
        Optional<Author>optionalAuthor=authorRepository.findByEmail(authorRequest.getEmail());
        if(optionalAuthor.isPresent()){
            throw new AuthorAlreadyPresentException("Author "+authorRequest.getName()+" is already present in the database with author id "+optionalAuthor.get().getId());
        }
        Author author=optionalAuthor.get();
        authorRepository.save(author);
        return "Author "+authorRequest.getName()+" is successfully added to the database";
    }


}
