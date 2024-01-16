package com.bvrit.cierclibrarymanagementsystem.dtos.requestdtos;

import com.bvrit.cierclibrarymanagementsystem.enums.GenreEnum;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookRequest {
    String name;
    int readTime;
    int  price;
    int noOfPages;
    double rating;
}
