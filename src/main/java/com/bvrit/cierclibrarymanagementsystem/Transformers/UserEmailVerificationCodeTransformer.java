package com.bvrit.cierclibrarymanagementsystem.Transformers;

import com.bvrit.cierclibrarymanagementsystem.dtos.requestdtos.UserEmailVerificationCodeRequest;
import com.bvrit.cierclibrarymanagementsystem.models.UserEmailVerificationCode;

public class UserEmailVerificationCodeTransformer {
    public static UserEmailVerificationCode UserEmailVerificationCodeRequestToUserEmailVerificationCode(UserEmailVerificationCodeRequest userEmailVerificationCodeRequest){
        return UserEmailVerificationCode.builder()
                .email(userEmailVerificationCodeRequest.getEmail())
                .verificationCode(userEmailVerificationCodeRequest.getVerificationCode())
                .build();
    }
}
