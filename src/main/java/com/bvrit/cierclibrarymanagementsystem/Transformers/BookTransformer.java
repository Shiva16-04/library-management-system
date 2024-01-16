package com.bvrit.cierclibrarymanagementsystem.Transformers;

import com.bvrit.cierclibrarymanagementsystem.dtos.requestdtos.BookRequest;
import com.bvrit.cierclibrarymanagementsystem.dtos.responsedtos.BookResponse;
import com.bvrit.cierclibrarymanagementsystem.dtos.responsedtos.GenreResponse;
import com.bvrit.cierclibrarymanagementsystem.enums.BookStatus;
import com.bvrit.cierclibrarymanagementsystem.models.Book;
import com.bvrit.cierclibrarymanagementsystem.models.Genre;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BookTransformer {
    public static Book bookRequestToBook(BookRequest bookRequest){
        return Book.builder()
                .name(bookRequest.getName())
                .price(bookRequest.getPrice())
                .readTime(bookRequest.getReadTime())
                .noOfPages(bookRequest.getNoOfPages())
                .rating(bookRequest.getRating())
                .bookStatus(BookStatus.AVAILABLE)
                .build();
    }
    public static BookResponse bookToBookResponse(Book book){
        List<String> genres=new ArrayList<>();
        for(Genre genre: book.getGenreList())genres.add(genre.getName().getDisplayName());
        return BookResponse.builder()
                .name(book.getName())
                .readTime(book.getReadTime())
                .price(book.getPrice())
                .noOfPages(book.getNoOfPages())
                .rating(book.getRating())
                .genres(genres)
                .authorName(null)
                .build();
    }
}
