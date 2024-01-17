package com.bvrit.cierclibrarymanagementsystem.controllerlayer;

import com.bvrit.cierclibrarymanagementsystem.dtos.requestdtos.BookRequest;
import com.bvrit.cierclibrarymanagementsystem.enums.BookStatus;
import com.bvrit.cierclibrarymanagementsystem.enums.GenreEnum;
import com.bvrit.cierclibrarymanagementsystem.repositorylayer.BookRepository;
import com.bvrit.cierclibrarymanagementsystem.servicelayer.BookService;
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
    public ResponseEntity addBook(@RequestBody BookRequest bookRequest, @RequestParam List<String> authorCodeList, @RequestParam List<GenreEnum>genreEnumList) {
        try {
            return new ResponseEntity<>(bookService.addBook(bookRequest, authorCodeList, genreEnumList), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/get-filtered-book-list")
    public ResponseEntity getFilteredBookList(@RequestParam(required = false) String bookCode,
                                              @RequestParam(required = false) String authorCode,
                                              @RequestParam(required = false) String name,
                                              @RequestParam(required = false) Integer readTime,
                                              @RequestParam(required = false) List<GenreEnum>genres,
                                              @RequestParam(required = false) List<BookStatus>statuses,
                                              @RequestParam(required = false) Double minRating,
                                              @RequestParam(required = false) Double maxRating,
                                              @RequestParam(required = false) Integer minPages,
                                              @RequestParam(required = false) Integer maxPages,
                                              @RequestParam(required = false) Integer minPrice,
                                              @RequestParam(required = false) Integer maxPrice){
        return new ResponseEntity<>(bookService.getFilteredBookResponseList(bookCode, authorCode, name, readTime,
                genres, statuses, minRating, maxRating, minPages, maxPages, minPrice, maxPrice), HttpStatus.ACCEPTED);
    }
    @PatchMapping("/update-books-status")
    public ResponseEntity updateBooksStatus(@RequestParam List<String>bookCodeList, @RequestParam BookStatus bookStatus){
        return new ResponseEntity<>(bookService.updateBooksStatus(bookCodeList, bookStatus),HttpStatus.OK);
    }

    @DeleteMapping("/delete-book")
    public ResponseEntity deleteBookByBookCode(@RequestParam List<String> bookCodeList){
        try {
            return new ResponseEntity<>(bookService.deleteBookList(bookCodeList),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }
}
