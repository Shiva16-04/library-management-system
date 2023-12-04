package com.bvrit.cierclibrarymanagementsystem.servicelayer;

import com.bvrit.cierclibrarymanagementsystem.dtos.requestdtos.BookRequest;
import com.bvrit.cierclibrarymanagementsystem.models.Book;
import org.springframework.stereotype.Service;


public interface BookService {
    public String addBook(BookRequest bookRequest, String authorCode)throws Exception;
    public Book findBookByBookCode(String bookCode)throws Exception;
}
