package com.bvrit.cierclibrarymanagementsystem.servicelayer;

import com.bvrit.cierclibrarymanagementsystem.dtos.requestdtos.BookRequest;
import com.bvrit.cierclibrarymanagementsystem.dtos.responsedtos.BookResponse;
import com.bvrit.cierclibrarymanagementsystem.enums.BookStatus;
import com.bvrit.cierclibrarymanagementsystem.enums.Genre;
import com.bvrit.cierclibrarymanagementsystem.exceptions.BookNotFoundException;
import com.bvrit.cierclibrarymanagementsystem.models.Book;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface BookService {
    public String addBook(BookRequest bookRequest, String authorCode)throws Exception;
    @Transactional
    public String updateBooksStatus(List<String>bookCodeList, BookStatus bookStatus);
    public BookResponse getBookByBookCode(String bookCode) throws BookNotFoundException;
    public List<BookResponse> getBookListByBookName(String bookName);
    public List<BookResponse> getBookListByGenre(Genre genre);
    public List<BookResponse> getBookListByBookStatus(BookStatus bookStatus);
    public Book findBookByBookCode(String bookCode)throws Exception;
}
