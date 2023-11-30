package com.bvrit.cierclibrarymanagementsystem.repositorylayer;

import com.bvrit.cierclibrarymanagementsystem.models.Author;
import jakarta.validation.constraints.Email;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Integer> {
    Optional<Author> findByEmail(String email);
}
