package com.bvrit.cierclibrarymanagementsystem.Transformers;

import com.bvrit.cierclibrarymanagementsystem.dtos.responsedtos.GenreResponse;
import com.bvrit.cierclibrarymanagementsystem.models.Genre;

public class GenreTransformer {
    public static GenreResponse genreToGenreResponse(Genre genre){
        return GenreResponse.builder()
                .name(genre.getName().getDisplayName())
                .build();
    }
}
