package com.bvrit.cierclibrarymanagementsystem.dtos.responsedtos;

import com.bvrit.cierclibrarymanagementsystem.enums.GenreEnum;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookResponse {
    String name;
    int readTime;
    int  price;
    int noOfPages;
    double rating;
    List<String> genres;
    String authorName;

}
