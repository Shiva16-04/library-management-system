package com.bvrit.cierclibrarymanagementsystem.servicelayer;

import com.bvrit.cierclibrarymanagementsystem.dtos.requestdtos.AuthorRequest;
import com.bvrit.cierclibrarymanagementsystem.dtos.responsedtos.AuthorResponse;
import com.bvrit.cierclibrarymanagementsystem.dtos.responsedtos.BookResponse;
import com.bvrit.cierclibrarymanagementsystem.exceptions.AuthorNotFoundException;
import com.bvrit.cierclibrarymanagementsystem.models.Author;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface AuthorService {
    public String addAuthor(AuthorRequest authorRequest)throws Exception;
    public AuthorResponse findAuthorByAuthorCode(String authorCode) throws AuthorNotFoundException;
    public AuthorResponse findAuthorByAuthorEmail(String email) throws AuthorNotFoundException;
    public List<BookResponse> getBookListByAuthorCode(String authorCode) throws AuthorNotFoundException;
}
