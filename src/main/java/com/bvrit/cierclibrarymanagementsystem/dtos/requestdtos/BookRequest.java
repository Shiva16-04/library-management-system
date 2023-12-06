package com.bvrit.cierclibrarymanagementsystem.dtos.requestdtos;

import com.bvrit.cierclibrarymanagementsystem.enums.Genre;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookRequest {
    String name;
    int readTime;
    int  price;
    int noOfPages;
    double rating;
    Genre genre;
}
