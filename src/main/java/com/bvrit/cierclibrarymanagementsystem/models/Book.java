package com.bvrit.cierclibrarymanagementsystem.models;

import com.bvrit.cierclibrarymanagementsystem.enums.Availability;
import com.bvrit.cierclibrarymanagementsystem.enums.Genre;
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
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Column(nullable = false, unique = true)
    String bookName;
    @Column(nullable = false)
    int  price;
    @Column(nullable = false)
    int noOfPages;
    @Column(nullable = false)
    double rating;
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    Genre genre;
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    Availability availability;

    @ManyToOne
    @JoinColumn
    Author author;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    List<Transaction> transactionList;
}
