package com.bvrit.cierclibrarymanagementsystem.repositorylayer;

import com.bvrit.cierclibrarymanagementsystem.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Integer> {
    Optional<Book>findByName(String name);
}
