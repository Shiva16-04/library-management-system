package com.bvrit.cierclibrarymanagementsystem.servicelayer.impl;

import com.bvrit.cierclibrarymanagementsystem.Transformers.UtilityTransformer;
import com.bvrit.cierclibrarymanagementsystem.dtos.requestdtos.UtilityRequest;
import com.bvrit.cierclibrarymanagementsystem.dtos.responsedtos.UtilityResponse;
import com.bvrit.cierclibrarymanagementsystem.exceptions.InvalidUtilityCodeException;
import com.bvrit.cierclibrarymanagementsystem.exceptions.UtilityAlreadyPresentException;
import com.bvrit.cierclibrarymanagementsystem.generators.UtilityCodeGenerator;
import com.bvrit.cierclibrarymanagementsystem.models.Utility;
import com.bvrit.cierclibrarymanagementsystem.repositorylayer.UtilityRepository;
import com.bvrit.cierclibrarymanagementsystem.servicelayer.BookAndUserAuditTrialService;
import com.bvrit.cierclibrarymanagementsystem.servicelayer.MailConfigurationService;
import com.bvrit.cierclibrarymanagementsystem.servicelayer.UtilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class UtilityServiceImpl implements UtilityService {
    @Autowired
    private UtilityRepository utilityRepository;
    @Autowired
    private UtilityCodeGenerator utilityCodeGenerator;
    @Autowired
    private MailConfigurationService mailConfigurationService;

    @Autowired
    private BookAndUserAuditTrialService bookAndUserAuditTrialService;

    public String addUtilityDetails(UtilityRequest utilityRequest) throws Exception {
        Optional<Utility> optionalUtility=utilityRepository.findBySenderEmail(utilityRequest.getSenderEmail());
        if(optionalUtility.isPresent()){
            throw new UtilityAlreadyPresentException("Utility is already present in the database with sender email "+utilityRequest.getSenderEmail());
        }
        Utility utility= UtilityTransformer.utilityRequestToUtility(utilityRequest);
        String utilityCode= utilityCodeGenerator.generate("UTL");
        utility.setUtilityCode(utilityCode);

        Utility savedUtility=utilityRepository.save(utility);
        return "Utility has been save to the db successfully";
    }

    public List<UtilityResponse> getUtilityDetailsList(){
        List<Utility> utilityList=utilityRepository.findAll();
        List<UtilityResponse>utilityResponseList=new ArrayList<>();
        for(Utility utility: utilityList){
            UtilityResponse utilityResponse=UtilityTransformer.utilityToUtilityResponse(utility);
            utilityResponseList.add(utilityResponse);
        }
        return utilityResponseList;
    }

    public String configureUtilityDetailsByUtilityCode(String utilityCode)throws Exception{
        Utility utility=findUtilityByUtilityCode(utilityCode);
        mailConfigurationService.updateSenderEmail(utility.getSenderEmail());
        bookAndUserAuditTrialService.updateFinePerDayAndMaxBooksIssuePerUser(utility.getFinePerDay(), utility.getMaxBooksIssueCountPerUser());
        return "Configured successfully";
    }

    private Utility findUtilityByUtilityCode(String utilityCode) throws InvalidUtilityCodeException {
        Optional<Utility>optionalUtility=utilityRepository.findByUtilityCode(utilityCode);
        if(!optionalUtility.isPresent()){
            throw new InvalidUtilityCodeException("Utility code "+utilityCode+" is invalid");
        }
        return optionalUtility.get();
    }
}
