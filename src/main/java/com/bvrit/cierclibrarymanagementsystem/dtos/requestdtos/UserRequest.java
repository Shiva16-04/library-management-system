package com.bvrit.cierclibrarymanagementsystem.dtos.requestdtos;

import com.bvrit.cierclibrarymanagementsystem.enums.BloodGroup;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRequest {
    String userName;
    BloodGroup bloodGroup;
    String contactNumber;
    String email;
    String emailVerificationCode;
    String password;
    String reTypePassword;
}
