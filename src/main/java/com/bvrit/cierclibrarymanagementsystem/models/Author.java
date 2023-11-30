package com.bvrit.cierclibrarymanagementsystem.models;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//to generate author id automatically
    Integer id;

    @Column(nullable = false)
    String name;

    int age;

    @Column(nullable = false)
    double rating;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    List<Book> bookList;
}
