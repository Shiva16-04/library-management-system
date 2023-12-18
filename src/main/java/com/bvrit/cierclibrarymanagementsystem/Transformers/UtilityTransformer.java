package com.bvrit.cierclibrarymanagementsystem.Transformers;

import com.bvrit.cierclibrarymanagementsystem.dtos.requestdtos.UtilityRequest;
import com.bvrit.cierclibrarymanagementsystem.dtos.responsedtos.UtilityResponse;
import com.bvrit.cierclibrarymanagementsystem.models.Utility;

public class UtilityTransformer {
    public static Utility utilityRequestToUtility(UtilityRequest utilityRequest){
        return Utility.builder()
                .senderEmail(utilityRequest.getSenderEmail())
                .finePerDay(utilityRequest.getFinePerDay())
                .maxBooksIssueCountPerUser(utilityRequest.getMaxBooksIssueCountPerUser())
                .build();
    }
    public static UtilityResponse utilityToUtilityResponse(Utility utility){
        return UtilityResponse.builder()
                .senderEmail(utility.getSenderEmail())
                .finePerDay(utility.getFinePerDay())
                .maxBooksIssueCountPerUser(utility.getMaxBooksIssueCountPerUser())
                .build();
    }
}
