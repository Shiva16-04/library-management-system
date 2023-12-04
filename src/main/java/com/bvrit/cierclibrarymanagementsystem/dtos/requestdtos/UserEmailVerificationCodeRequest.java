package com.bvrit.cierclibrarymanagementsystem.dtos.requestdtos;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class UserEmailVerificationCodeRequest {
    String email;
    String verificationCode;
}
