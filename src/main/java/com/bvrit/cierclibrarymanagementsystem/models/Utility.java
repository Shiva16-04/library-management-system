package com.bvrit.cierclibrarymanagementsystem.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Utility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(nullable = false, unique = true)
    String utilityCode;
    @Column(nullable = false)
    @Email(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{}|~^.-]+@bvrit\\.ac\\.in$", message = "should be a valid college email address with domain bvrit.ac.in")
    //validating domain specific email using RFC 5322 standard and preventing possible sql injection using ' and | characters
    String senderEmail;
    @Column(nullable = false)
    int finePerDay;
    @Column(nullable = false)
    int maxBooksIssueCountPerUser;
}
