package com.bvrit.cierclibrarymanagementsystem.servicelayer;

import com.bvrit.cierclibrarymanagementsystem.enums.GenreEnum;
import com.bvrit.cierclibrarymanagementsystem.models.Genre;

import java.util.List;

public interface GenreService {
    public Object addGenre(List<GenreEnum> genreEnumList)throws Exception;
    public List<Genre> getGenreList(List<GenreEnum> genreEnumList);
}
