package com.bvrit.cierclibrarymanagementsystem.dtos.responsedtos;

import com.bvrit.cierclibrarymanagementsystem.enums.BloodGroup;
import com.bvrit.cierclibrarymanagementsystem.enums.Occupation;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    String userName;
    Occupation occupation;
    BloodGroup bloodGroup;
    String contactNumber;
    String email;
}
