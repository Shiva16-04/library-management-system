package com.bvrit.cierclibrarymanagementsystem.Transformers;

import com.bvrit.cierclibrarymanagementsystem.dtos.responsedtos.BookAndUserAuditTrialResponse;
import com.bvrit.cierclibrarymanagementsystem.models.BookAndUserAuditTrial;

public class BookAndUserAuditTrialTransformer {
    public static BookAndUserAuditTrialResponse bookAndUserAuditTrialTransformerToBookAndUserAuditTrialTransformerResponse(BookAndUserAuditTrial bookAndUserAuditTrial){
        return BookAndUserAuditTrialResponse.builder()
                .issueDate(bookAndUserAuditTrial.getIssueDate())
                .lastModifiedOn(bookAndUserAuditTrial.getLastModifiedOn())
                .status(bookAndUserAuditTrial.getStatus())
                .returnDate(bookAndUserAuditTrial.getReturnDate())
                .cardCode(bookAndUserAuditTrial.getCard().getCardCode())
                .bookCode(bookAndUserAuditTrial.getBook().getBookCode())
                .build();
    }
}
