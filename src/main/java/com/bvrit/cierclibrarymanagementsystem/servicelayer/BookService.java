package com.bvrit.cierclibrarymanagementsystem.servicelayer;

import com.bvrit.cierclibrarymanagementsystem.dtos.requestdtos.BookRequest;
import com.bvrit.cierclibrarymanagementsystem.dtos.responsedtos.BookResponse;
import com.bvrit.cierclibrarymanagementsystem.exceptions.BookNotFoundException;
import com.bvrit.cierclibrarymanagementsystem.models.Book;
import org.springframework.stereotype.Service;

import java.util.List;


public interface BookService {
    public String addBook(BookRequest bookRequest, String authorCode)throws Exception;
    public BookResponse getBookByBookCode(String bookCode) throws BookNotFoundException;
    public List<BookResponse> getBookListByBookName(String bookName);
    public Book findBookByBookCode(String bookCode)throws Exception;
}
