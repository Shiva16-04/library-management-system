package com.bvrit.cierclibrarymanagementsystem.servicelayer.impl;

import com.bvrit.cierclibrarymanagementsystem.enums.GenreEnum;
import com.bvrit.cierclibrarymanagementsystem.exceptions.GenreAlreadyPresentException;
import com.bvrit.cierclibrarymanagementsystem.models.Genre;
import com.bvrit.cierclibrarymanagementsystem.repositorylayer.GenreRepository;
import com.bvrit.cierclibrarymanagementsystem.servicelayer.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class GenreServiceImpl implements GenreService {
    @Autowired
    private GenreRepository genreRepository;

    public Object addGenre(List<GenreEnum> genreEnumList)throws Exception{
        List<String> alreadyPresentGenreList = new ArrayList<>();
        List<String> alreadyNotPresentGenreList = new ArrayList<>();

        for (GenreEnum genreEnum : genreEnumList) {
            Optional<Genre> optionalGenre = genreRepository.findByName(genreEnum);

            if (optionalGenre.isEmpty()) {
                Genre genre = new Genre();
                genre.setName(genreEnum);
                genreRepository.save(genre);
                alreadyNotPresentGenreList.add(genreEnum.getDisplayName());
            } else {
                alreadyPresentGenreList.add(genreEnum.getDisplayName());
            }
        }

        if (!alreadyPresentGenreList.isEmpty()) {
            throw new GenreAlreadyPresentException("Cannot add. Genre(s) " + alreadyPresentGenreList + " is/are present in the database");
        } else {
            return "Genre(s) " + alreadyNotPresentGenreList + " added to the database successfully";
        }

    }
    public List<Genre> getGenreList(List<GenreEnum> genreEnumList){
        List<Genre>genreList=genreRepository.findByNameIn(genreEnumList);
        return genreList;
    }
}
