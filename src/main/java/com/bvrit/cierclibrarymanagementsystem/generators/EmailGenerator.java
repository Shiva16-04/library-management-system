package com.bvrit.cierclibrarymanagementsystem.generators;

import org.springframework.stereotype.Component;

import java.util.UUID;
@Component
public class EmailGenerator {

    public String userEmailValidationCodeGenerator(){
        UUID uuid= UUID.randomUUID();
        return ""+uuid;
    }
    public String userSuccessfulRegistrationMessageGenerator(String name){

        String body= "Hello "+name+" !!!\nWelcome to the English Readers' Club.\nYou have been successfully registered with us.";
        return body;
    }

}
