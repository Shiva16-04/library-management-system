package com.bvrit.cierclibrarymanagementsystem.dtos.responsedtos;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthorResponse {
    String name;
    int age;
    double rating;
    String email;

}
