package com.bvrit.cierclibrarymanagementsystem.controllerlayer;

import com.bvrit.cierclibrarymanagementsystem.dtos.requestdtos.AuthorRequest;
import com.bvrit.cierclibrarymanagementsystem.exceptions.AuthorNotFoundException;
import com.bvrit.cierclibrarymanagementsystem.servicelayer.AuthorService;
import com.bvrit.cierclibrarymanagementsystem.servicelayer.impl.AuthorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/author")
public class AuthorController {
    @Autowired
    private AuthorService authorService;

    @PostMapping("/add-author")
    public ResponseEntity addAuthor(@RequestBody AuthorRequest authorRequest){
        try {
            return new ResponseEntity<>(authorService.addAuthor(authorRequest), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/find-author-by-author-code")
    public ResponseEntity findAuthorByAuthorCode(@RequestParam String authorCode){
        try {
            return new ResponseEntity<>(authorService.findAuthorByAuthorCode(authorCode), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/find-author-by-author-email")
    public ResponseEntity findAuthorByAuthorEmail(@RequestParam String authorEmail){
        try {
            return new ResponseEntity<>(authorService.findAuthorByAuthorEmail(authorEmail), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/get-book-list-by-author-code")
    public ResponseEntity getBookListByAuthorCode(@RequestParam String authorCode){
        try {
            return new ResponseEntity<>(authorService.getBookListByAuthorCode(authorCode), HttpStatus.OK);
        } catch (AuthorNotFoundException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/delete-author-by-author-code")
    public ResponseEntity deleteAuthorByAuthorCode(@RequestParam String authorCode){
        try {
            return new ResponseEntity<>("", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
