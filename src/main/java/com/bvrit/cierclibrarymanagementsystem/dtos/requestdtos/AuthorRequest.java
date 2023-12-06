package com.bvrit.cierclibrarymanagementsystem.dtos.requestdtos;

import jakarta.persistence.Column;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthorRequest {
    String name;
    int age;
    double rating;
    String email;
}
