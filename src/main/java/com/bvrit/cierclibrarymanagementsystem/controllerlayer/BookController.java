package com.bvrit.cierclibrarymanagementsystem.controllerlayer;

import com.bvrit.cierclibrarymanagementsystem.dtos.requestdtos.BookRequest;
import com.bvrit.cierclibrarymanagementsystem.dtos.requestdtos.UserRequest;
import com.bvrit.cierclibrarymanagementsystem.servicelayer.BookService;
import com.bvrit.cierclibrarymanagementsystem.servicelayer.impl.BookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    private BookServiceImpl bookServiceImpl;

    @PostMapping("/addDetails")
    public ResponseEntity addDetails(@RequestBody BookRequest bookRequest, @RequestParam int authorId){
        try {
            return new ResponseEntity<>(bookServiceImpl.addDetails(bookRequest, authorId), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
