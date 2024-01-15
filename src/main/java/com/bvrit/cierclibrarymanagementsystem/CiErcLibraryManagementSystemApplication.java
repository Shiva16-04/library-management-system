package com.bvrit.cierclibrarymanagementsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CiErcLibraryManagementSystemApplication {
	public static void main(String[] args) {
		SpringApplication.run(CiErcLibraryManagementSystemApplication.class, args);
	}

}
