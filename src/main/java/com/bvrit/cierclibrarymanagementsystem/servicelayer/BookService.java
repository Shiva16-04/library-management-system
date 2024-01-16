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
    public String addBook(BookRequest bookRequest, List<String> authorCodeList, List<GenreEnum>genreEnumList)throws Exception;
    @Transactional
    public String updateBooksStatus(List<String>bookCodeList, BookStatus bookStatus);
    public BookResponse getBookByBookCode(String bookCode) throws BookNotFoundException;
    public List<BookResponse> getBookListByBookName(String bookName);
    public List<BookResponse> getBookListByGenre(GenreEnum genreEnum);
    public List<BookResponse> getBookListByBookStatus(BookStatus bookStatus);
    public Book findBookByBookCode(String bookCode)throws Exception;
    public String deleteBookByBookCode(List<String> bookCodeList) throws Exception;
}
