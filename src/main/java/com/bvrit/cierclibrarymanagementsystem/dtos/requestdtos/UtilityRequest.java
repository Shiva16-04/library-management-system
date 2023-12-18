package com.bvrit.cierclibrarymanagementsystem.dtos.requestdtos;

import jakarta.persistence.Column;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UtilityRequest {
    String senderEmail;
    int finePerDay;
    int maxBooksIssueCountPerUser;
}
