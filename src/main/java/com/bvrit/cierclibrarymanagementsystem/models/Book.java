package com.bvrit.cierclibrarymanagementsystem.models;

import com.bvrit.cierclibrarymanagementsystem.enums.BookStatus;
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
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Column(unique = true, nullable = false)
    String bookCode;
    @Column(nullable = false, unique = true)
    String name;
    @Column(nullable = false) //no of days allotted to read the book
    int  readTime;
    @Column(nullable = false)
    int  price;
    @Column(nullable = false)
    int noOfPages;
    @Column(nullable = false)
    double rating;
    @ManyToMany(cascade = CascadeType.PERSIST)
    List<Genre> genreList;
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    BookStatus bookStatus;
    String Comment;
    @ManyToMany(mappedBy = "bookList", cascade = CascadeType.PERSIST)
    List<Author> authorList;
    @ManyToOne
    Card card;

}
