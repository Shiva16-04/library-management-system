package com.bvrit.cierclibrarymanagementsystem.servicelayer;

import com.bvrit.cierclibrarymanagementsystem.dtos.requestdtos.AuthorRequest;
import com.bvrit.cierclibrarymanagementsystem.dtos.responsedtos.AuthorResponse;
import com.bvrit.cierclibrarymanagementsystem.models.Author;

import java.util.List;


public interface AuthorService {
    public List<AuthorResponse> getFilteredAuthorResponseList(String authorCode, String bookCode, String name, Integer minAge, Integer maxAge,
                                                              Double minRating, Double maxRating, String email);
    public List<Author> getAuthorList(List<String>authorCodeList);
    public String addAuthor(AuthorRequest authorRequest)throws Exception;
    public String deleteAuthor(String authorCode) throws Exception;


}
