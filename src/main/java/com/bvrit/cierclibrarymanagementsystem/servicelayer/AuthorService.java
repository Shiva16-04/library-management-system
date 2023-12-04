package com.bvrit.cierclibrarymanagementsystem.servicelayer;

import com.bvrit.cierclibrarymanagementsystem.dtos.requestdtos.AuthorRequest;
import com.bvrit.cierclibrarymanagementsystem.models.Author;
import org.springframework.stereotype.Service;

import java.util.Optional;


public interface AuthorService {
    public String addAuthor(AuthorRequest authorRequest)throws Exception;
    public Optional<Author> findAuthorByAuthorCode(String authorCode);
}
