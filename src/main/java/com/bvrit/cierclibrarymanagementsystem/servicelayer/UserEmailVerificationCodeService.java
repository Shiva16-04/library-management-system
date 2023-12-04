package com.bvrit.cierclibrarymanagementsystem.servicelayer;

import com.bvrit.cierclibrarymanagementsystem.dtos.requestdtos.UserEmailVerificationCodeRequest;
import com.bvrit.cierclibrarymanagementsystem.models.UserEmailVerificationCode;

import java.util.Optional;

public interface UserEmailVerificationCodeService {
    public Optional<UserEmailVerificationCode> findUserEmailVerificationCode(String email);
    public void addUserEmailVerificationCode(UserEmailVerificationCodeRequest userEmailVerificationCodeRequest);
}
