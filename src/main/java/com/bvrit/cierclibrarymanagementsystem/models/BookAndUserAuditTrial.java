package com.bvrit.cierclibrarymanagementsystem.models;

import com.bvrit.cierclibrarymanagementsystem.enums.BookAndUserAuditStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
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
    Date issueDate;

    @UpdateTimestamp
    Date lastModifiedOn;

    @Enumerated(value = EnumType.STRING)
    BookAndUserAuditStatus status;

    LocalDate returnDate;

    @ManyToOne
    Card card;

    @ManyToOne
    Book book;

}
