package com.bvrit.cierclibrarymanagementsystem.servicelayer;

import com.bvrit.cierclibrarymanagementsystem.dtos.requestdtos.UtilityRequest;
import com.bvrit.cierclibrarymanagementsystem.dtos.responsedtos.UtilityResponse;

import java.util.List;

public interface UtilityService {
    public String addUtilityDetails(UtilityRequest utilityRequest) throws Exception;
    public List<UtilityResponse> getUtilityDetailsList();
    public String configureUtilityDetailsByUtilityCode(String utilityCode)throws Exception;
}
