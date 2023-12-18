package com.bvrit.cierclibrarymanagementsystem.servicelayer.impl;

import com.bvrit.cierclibrarymanagementsystem.Transformers.UtilityTransformer;
import com.bvrit.cierclibrarymanagementsystem.dtos.requestdtos.UtilityRequest;
import com.bvrit.cierclibrarymanagementsystem.dtos.responsedtos.UtilityResponse;
import com.bvrit.cierclibrarymanagementsystem.exceptions.InvalidUtilityCodeException;
import com.bvrit.cierclibrarymanagementsystem.exceptions.UtilityAlreadyPresentException;
import com.bvrit.cierclibrarymanagementsystem.generators.UtilityCodeGenerator;
import com.bvrit.cierclibrarymanagementsystem.models.Utility;
import com.bvrit.cierclibrarymanagementsystem.repositorylayer.UtilityRepository;
import com.bvrit.cierclibrarymanagementsystem.servicelayer.AdminService;
import com.bvrit.cierclibrarymanagementsystem.servicelayer.BookAndUserAuditTrialService;
import com.bvrit.cierclibrarymanagementsystem.servicelayer.MailConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

}
