package com.bvrit.cierclibrarymanagementsystem.models;

import com.bvrit.cierclibrarymanagementsystem.enums.CardStatus;
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
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Column(nullable = false, unique = true) //it will be the local part of user email id which is the roll number of the student
    String cardCode;

    @Column(nullable = false)
    int fineAmount;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    CardStatus status;

    @Column(nullable = false)
    Integer numberOfBooksIssued;

    @OneToOne
    @JoinColumn
    User user;

    @OneToMany
    List<BookAndUserAuditTrial> bookAndUserAuditTrialList;

    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL)
    List<Book> bookList;
}
