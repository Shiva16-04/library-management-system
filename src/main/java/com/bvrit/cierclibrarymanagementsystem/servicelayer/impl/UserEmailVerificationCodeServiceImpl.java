package com.bvrit.cierclibrarymanagementsystem.servicelayer.impl;

import com.bvrit.cierclibrarymanagementsystem.Transformers.UserEmailVerificationCodeTransformer;
import com.bvrit.cierclibrarymanagementsystem.dtos.requestdtos.UserEmailVerificationCodeRequest;
import com.bvrit.cierclibrarymanagementsystem.models.UserEmailVerificationCode;
import com.bvrit.cierclibrarymanagementsystem.repositorylayer.UserEmailVerificationCodeRepository;
import com.bvrit.cierclibrarymanagementsystem.servicelayer.UserEmailVerificationCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserEmailVerificationCodeServiceImpl implements UserEmailVerificationCodeService {
    @Autowired
    private UserEmailVerificationCodeRepository userEmailVerificationCodeRepository;

    public Optional<UserEmailVerificationCode> findUserEmailVerificationCode(String email){
        Optional<UserEmailVerificationCode>userEmailVerificationCode=userEmailVerificationCodeRepository.findByEmail(email);
        return userEmailVerificationCode;
    }
    public void addUserEmailVerificationCode(UserEmailVerificationCodeRequest userEmailVerificationCodeRequest){
        UserEmailVerificationCode userEmailVerificationCode= UserEmailVerificationCodeTransformer.UserEmailVerificationCodeRequestToUserEmailVerificationCode(userEmailVerificationCodeRequest);
        userEmailVerificationCodeRepository.save(userEmailVerificationCode);
    }
}
