package com.bvrit.cierclibrarymanagementsystem.models;

import com.bvrit.cierclibrarymanagementsystem.enums.GenreEnum;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Column(nullable = false, unique = true)
    @Enumerated(value = EnumType.STRING)
    GenreEnum name;
    @ManyToMany(mappedBy = "genreList")
    List<Book> bookList;
}
