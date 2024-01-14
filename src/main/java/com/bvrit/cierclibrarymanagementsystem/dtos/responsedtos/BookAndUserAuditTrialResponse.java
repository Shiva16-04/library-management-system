package com.bvrit.cierclibrarymanagementsystem.dtos.responsedtos;

import com.bvrit.cierclibrarymanagementsystem.enums.BookAndUserAuditStatus;
import com.bvrit.cierclibrarymanagementsystem.enums.ReturnItem;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookAndUserAuditTrialResponse {
    String issueReturnCode;
    LocalDate issueDate;
    LocalDate returnDate;
    LocalDate returnedOn;
    ReturnItem returnItem;
    LocalDateTime lastModifiedOn;
    @Enumerated(value = EnumType.STRING)
    BookAndUserAuditStatus status;
    String cardCode;
    String bookCode;
}
