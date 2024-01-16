package com.bvrit.cierclibrarymanagementsystem.controllerlayer;

import com.bvrit.cierclibrarymanagementsystem.enums.GenreEnum;
import com.bvrit.cierclibrarymanagementsystem.servicelayer.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/genre")
@RestController
public class GenreController {
    @Autowired
    private GenreService genreService;

    @PostMapping("/add-genre")
    public ResponseEntity addGenre(@RequestParam List<GenreEnum> genreEnumList){
        try {
            return new ResponseEntity(genreService.addGenre(genreEnumList), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
