package com.bvrit.cierclibrarymanagementsystem.models;

import com.bvrit.cierclibrarymanagementsystem.enums.BloodGroup;
import com.bvrit.cierclibrarymanagementsystem.enums.Occupation;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(nullable = false)
    String userName;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    Occupation occupation;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    BloodGroup bloodGroup;

    @Column(nullable = false, unique = true)
    @Pattern(regexp="[0-9]{10}", message="Should be a valid contact number")
    String contactNumber;

    @Column(nullable = false, unique = true)
    @Email(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{}|~^.-]+@bvrit\\.ac\\.in$", message = "should be a valid college email address with domain bvrit.ac.in")
    //validating domain specific email using RFC 5322 standard and preventing possible sql injection using ' and | characters
    String email;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @JoinColumn
    Card card;
}
