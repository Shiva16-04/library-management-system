package com.bvrit.cierclibrarymanagementsystem.Transformers;

import com.bvrit.cierclibrarymanagementsystem.dtos.requestdtos.AuthorRequest;
import com.bvrit.cierclibrarymanagementsystem.dtos.responsedtos.AuthorResponse;
import com.bvrit.cierclibrarymanagementsystem.models.Author;

import java.io.IOException;

public class AuthorTransformer {
    public static Author authorRequestToAuthor(AuthorRequest authorRequest) throws IOException {
        return Author.builder()
                .name(authorRequest.getName())
                .age(authorRequest.getAge())
                .rating(authorRequest.getRating())
                .email(authorRequest.getEmail())
                .build();
    }
    public static AuthorResponse authorToAuthorResponse(Author author){
        return AuthorResponse.builder()
                .name(author.getName())
                .age(author.getAge())
                .rating(author.getRating())
                .email(author.getEmail())
                .build();
    }
}
