package com.bvrit.cierclibrarymanagementsystem.repositorylayer;

import com.bvrit.cierclibrarymanagementsystem.enums.GenreEnum;
import com.bvrit.cierclibrarymanagementsystem.models.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GenreRepository extends JpaRepository<Genre, Integer> {
    Optional<Genre> findByName(GenreEnum genreEnum);
    List<Genre> findByNameIn(List<GenreEnum>genreEnumList);
}
