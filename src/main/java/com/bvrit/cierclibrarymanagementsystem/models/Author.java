package com.bvrit.cierclibrarymanagementsystem.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
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
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//to generate author id automatically
    int id;

    @Column(nullable = false, unique = true)
    String authorCode;

    @Column(nullable = false)
    String name;

    int age;

    @Column(nullable = false)
    double rating;

    @Lob
    @Column(columnDefinition="MEDIUMBLOB")
    private byte[] authorImage;

    @Column(nullable = false, unique = true)
    @Email(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", message = "should be a valid email address")
    //validating email using RFC 5322 standard
    String email;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    List<Book> bookList;
}
