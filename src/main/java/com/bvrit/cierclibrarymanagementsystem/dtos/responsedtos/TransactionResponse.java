package com.bvrit.cierclibrarymanagementsystem.dtos.responsedtos;

import com.bvrit.cierclibrarymanagementsystem.enums.TransactionStatus;
import jakarta.persistence.Column;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransactionResponse {
    LocalDate creationDate;
    TransactionStatus transactionStatus;
    String transactedOn;
    String transactedBy;
}
