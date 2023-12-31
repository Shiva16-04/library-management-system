package com.bvrit.cierclibrarymanagementsystem.Transformers;

import com.bvrit.cierclibrarymanagementsystem.dtos.requestdtos.BookRequest;
import com.bvrit.cierclibrarymanagementsystem.dtos.responsedtos.BookResponse;
import com.bvrit.cierclibrarymanagementsystem.enums.BookStatus;
import com.bvrit.cierclibrarymanagementsystem.models.Book;

public class BookTransformer {
    public static Book bookRequestToBook(BookRequest bookRequest){
        return Book.builder()
                .name(bookRequest.getName())
                .price(bookRequest.getPrice())
                .readTime(bookRequest.getReadTime())
                .noOfPages(bookRequest.getNoOfPages())
                .rating(bookRequest.getRating())
                .genre(bookRequest.getGenre())
                .bookStatus(BookStatus.AVAILABLE)
                .build();
    }
    public static BookResponse bookToBookResponse(Book book){
        return BookResponse.builder()
                .name(book.getName())
                .readTime(book.getReadTime())
                .price(book.getPrice())
                .noOfPages(book.getNoOfPages())
                .rating(book.getRating())
                .genre(book.getGenre())
                .authorName(book.getAuthor().getName())
                .build();
    }
}
