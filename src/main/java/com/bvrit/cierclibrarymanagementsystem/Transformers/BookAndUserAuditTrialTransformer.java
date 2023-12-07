package com.bvrit.cierclibrarymanagementsystem.Transformers;

import com.bvrit.cierclibrarymanagementsystem.dtos.responsedtos.BookAndUserAuditTrialResponse;
import com.bvrit.cierclibrarymanagementsystem.enums.BookAndUserAuditStatus;
import com.bvrit.cierclibrarymanagementsystem.models.BookAndUserAuditTrial;

import java.time.LocalDate;

public class BookAndUserAuditTrialTransformer {
    public static BookAndUserAuditTrial bookAndUserAuditTrialCreation(int bookReadTime){
        return BookAndUserAuditTrial.builder()
                .returnDate(LocalDate.now().plusDays(bookReadTime))
                .status(BookAndUserAuditStatus.ISSUED)
                .build();
    }
    public static BookAndUserAuditTrialResponse bookAndUserAuditTrialTransformerToBookAndUserAuditTrialTransformerResponse(BookAndUserAuditTrial bookAndUserAuditTrial){
        return BookAndUserAuditTrialResponse.builder()
                .issueDate(bookAndUserAuditTrial.getIssueDate())
                .returnDate(bookAndUserAuditTrial.getReturnDate())
                .returnedOn(bookAndUserAuditTrial.getReturnedOn())
                .lastModifiedOn(bookAndUserAuditTrial.getLastModifiedOn())
                .status(bookAndUserAuditTrial.getStatus())
                .cardCode(bookAndUserAuditTrial.getCardCode())
                .bookCode(bookAndUserAuditTrial.getBookCode())
                .build();
    }
}
