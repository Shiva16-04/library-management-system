package com.bvrit.cierclibrarymanagementsystem.models;

import com.bvrit.cierclibrarymanagementsystem.enums.BookAndUserAuditStatus;
import com.bvrit.cierclibrarymanagementsystem.enums.ReturnItem;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    @Column(nullable = false, unique = true)
    String issueReturnCode;
    @CreationTimestamp
    LocalDate issueDate;
    @Column(nullable = false)
    LocalDate returnDate;
    LocalDate returnedOn;
    @Enumerated(value = EnumType.STRING)
    ReturnItem returnItem;
    @UpdateTimestamp
    LocalDateTime lastModifiedOn;
    @Enumerated(value = EnumType.STRING)
    BookAndUserAuditStatus status;
    String cardCode;
    String bookCode;
}
