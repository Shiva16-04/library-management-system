package com.bvrit.cierclibrarymanagementsystem.controllerlayer;

import com.bvrit.cierclibrarymanagementsystem.dtos.requestdtos.BookRequest;
import com.bvrit.cierclibrarymanagementsystem.servicelayer.BookService;
import com.bvrit.cierclibrarymanagementsystem.servicelayer.impl.BookServiceImpl;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    private BookService bookService;

    @PostMapping("/add-book")
    public ResponseEntity addBook(@RequestBody BookRequest bookRequest, @RequestParam String authorCode) {
        try {
            return new ResponseEntity<>(bookService.addBook(bookRequest, authorCode), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
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
            return new ResponseEntity<>(bookService.getBookListByBookName(bookName), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
