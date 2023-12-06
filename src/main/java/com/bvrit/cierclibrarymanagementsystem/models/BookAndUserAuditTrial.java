package com.bvrit.cierclibrarymanagementsystem.models;

import com.bvrit.cierclibrarymanagementsystem.enums.BookAndUserAuditStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookAndUserAuditTrial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @CreationTimestamp
    LocalDate issueDate;

    @Column(nullable = false)
    LocalDate returnDate;

    LocalDate returnedOn;

    @UpdateTimestamp
    LocalDateTime lastModifiedOn;

    @Enumerated(value = EnumType.STRING)
    BookAndUserAuditStatus status;

    @ManyToOne
    Card card;

    @ManyToOne
    Book book;

}
