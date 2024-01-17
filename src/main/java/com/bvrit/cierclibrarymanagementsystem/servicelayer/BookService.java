package com.bvrit.cierclibrarymanagementsystem.servicelayer;

import com.bvrit.cierclibrarymanagementsystem.dtos.requestdtos.BookRequest;
import com.bvrit.cierclibrarymanagementsystem.dtos.responsedtos.BookResponse;
import com.bvrit.cierclibrarymanagementsystem.enums.BookStatus;
import com.bvrit.cierclibrarymanagementsystem.enums.GenreEnum;
import com.bvrit.cierclibrarymanagementsystem.exceptions.BookNotFoundException;
import com.bvrit.cierclibrarymanagementsystem.models.Book;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface BookService {
    public List<BookResponse> getFilteredBookResponseList(String bookCode, String authorCode, String name, Integer readTime,
                                                          List<GenreEnum>genres, List<BookStatus>statuses,
                                                          Double minRating, Double maxRating,
                                                          Integer minPages, Integer maxPages,
                                                          Integer minPrice, Integer maxPrice);
    public List<Book> getFilteredBookList(String bookCode, String authorCode, String name,
                                                            Integer readTime,
                                                            List<GenreEnum>genres, List<BookStatus>statuses,
                                                            Double minRating, Double maxRating,
                                                            Integer minPages, Integer maxPages,
                                                            Integer minPrice, Integer maxPrice);
    @Transactional
    public String updateBooksStatus(List<String>bookCodeList, BookStatus bookStatus);
    public String addBook(BookRequest bookRequest, List<String> authorCodeList, List<GenreEnum>genreEnumList)throws Exception;
    public String deleteBookList(List<String> bookCodeList) throws Exception;
}
