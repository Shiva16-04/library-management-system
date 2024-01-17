package com.bvrit.cierclibrarymanagementsystem.controllerlayer;

import com.bvrit.cierclibrarymanagementsystem.dtos.requestdtos.AuthorRequest;
import com.bvrit.cierclibrarymanagementsystem.servicelayer.AuthorService;
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

    @DeleteMapping("/delete-author")
    public ResponseEntity deleteAuthor(@RequestParam String authorCode){
        try {
            return new ResponseEntity<>(authorService.deleteAuthor(authorCode), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/get-filtered-author-list")
    public ResponseEntity getAuthorFilteredList(@RequestParam(required = false) String authorCode,
                                                @RequestParam(required = false) String bookCode,
                                                @RequestParam(required = false) String name,
                                                @RequestParam(required = false) Integer minAge,
                                                @RequestParam(required = false) Integer maxAge,
                                                @RequestParam(required = false) Double minRating,
                                                @RequestParam(required = false) Double maxRating,
                                                @RequestParam(required = false) String email){
        return new ResponseEntity<>(authorService.getFilteredAuthorResponseList(authorCode, bookCode, name, minAge, maxAge, minRating, maxRating, email), HttpStatus.ACCEPTED);

    }
}
