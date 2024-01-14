package com.bvrit.cierclibrarymanagementsystem.controllerlayer;

import com.bvrit.cierclibrarymanagementsystem.dtos.requestdtos.BookRequest;
import com.bvrit.cierclibrarymanagementsystem.enums.BookStatus;
import com.bvrit.cierclibrarymanagementsystem.enums.CardStatus;
import com.bvrit.cierclibrarymanagementsystem.enums.Genre;
import com.bvrit.cierclibrarymanagementsystem.repositorylayer.BookRepository;
import com.bvrit.cierclibrarymanagementsystem.servicelayer.BookService;
import com.bvrit.cierclibrarymanagementsystem.servicelayer.CardService;
import com.bvrit.cierclibrarymanagementsystem.servicelayer.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    private BookService bookService;
    @Autowired
    private UserService userService;
    @Autowired
    private BookRepository bookRepository;



    @PostMapping("/add-book")
    public ResponseEntity addBook(@RequestBody BookRequest bookRequest, @RequestParam String authorCode) {
        try {
            return new ResponseEntity<>(bookService.addBook(bookRequest, authorCode), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @PatchMapping("/update-books-status")
    public ResponseEntity updateBooksStatus(@RequestParam List<String>bookCodeList, @RequestParam BookStatus bookStatus){
        return new ResponseEntity<>(bookService.updateBooksStatus(bookCodeList, bookStatus),HttpStatus.OK);
    }
    @GetMapping("/get-book-by-book-code/{bookCode}")
    public ResponseEntity getBookByBookCode(@PathVariable("bookCode") String bookCode) {
        try {
            return new ResponseEntity<>(bookService.getBookByBookCode(bookCode), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/get-book-list-by-book-name")
    public ResponseEntity getBookListByBookName(@RequestParam String bookName) {
        try {
            return new ResponseEntity<>(bookService.getBookListByBookName(bookName), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/get-book-list-by-genre")
    public ResponseEntity getBookListByGenre(@RequestParam Genre genre){
        return new ResponseEntity(bookService.getBookListByGenre(genre),HttpStatus.OK);
    }

    @GetMapping("/get-book-list-by-book-status")
    public ResponseEntity getBookListByBookStatus(@RequestParam BookStatus bookStatus){
        return new ResponseEntity(bookService.getBookListByBookStatus(bookStatus),HttpStatus.OK);
    }


    @DeleteMapping("/delete-book")
    public ResponseEntity deleteBookByBookCode(@RequestParam List<String> bookCodeList){
        try {
            return new ResponseEntity<>(bookService.deleteBookByBookCode(bookCodeList),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }


}
