package com.bvrit.cierclibrarymanagementsystem.dtos.requestdtos;

import com.bvrit.cierclibrarymanagementsystem.enums.BloodGroup;
import com.bvrit.cierclibrarymanagementsystem.enums.Occupation;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRequest {
    String userName;
    Occupation occupation;
    BloodGroup bloodGroup;
    String contactNumber;
    String email;
}
