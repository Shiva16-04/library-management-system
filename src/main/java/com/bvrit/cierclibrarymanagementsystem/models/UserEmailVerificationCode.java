package com.bvrit.cierclibrarymanagementsystem.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserEmailVerificationCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Column(unique = true, nullable = false)
    @Email(message = "should be a valid college email address with domain bvrit.ac.in")
    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{}|~^.-]+@bvrit\\.ac\\.in$", message = "should be a valid college email address with domain bvrit.ac.in")
    String email;
    @Column(nullable = false)
    String verificationCode;
}
