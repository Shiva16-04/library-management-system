package com.bvrit.cierclibrarymanagementsystem.servicelayer.impl;

import com.bvrit.cierclibrarymanagementsystem.Transformers.UserTransformer;
import com.bvrit.cierclibrarymanagementsystem.dtos.requestdtos.UserRequest;
import com.bvrit.cierclibrarymanagementsystem.exceptions.InValidEmailVerificationCodeException;
import com.bvrit.cierclibrarymanagementsystem.exceptions.UserAlreadyPresentException;
import com.bvrit.cierclibrarymanagementsystem.exceptions.UserNotFoundException;
import com.bvrit.cierclibrarymanagementsystem.generators.EmailGenerator;
import com.bvrit.cierclibrarymanagementsystem.models.Card;
import com.bvrit.cierclibrarymanagementsystem.models.User;
import com.bvrit.cierclibrarymanagementsystem.repositorylayer.UserRepository;
import com.bvrit.cierclibrarymanagementsystem.servicelayer.CardService;
import com.bvrit.cierclibrarymanagementsystem.servicelayer.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private CardServiceImpl cardServiceImpl;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private EmailGenerator emailGenerator;
    private String code;

    public String addUser(UserRequest userRequest)throws Exception{
        Optional<User>optionalUser=userRepository.findByEmail(userRequest.getEmail());
        if(optionalUser.isPresent()){
            throw new UserAlreadyPresentException("User already Registered");
        }
        //validating the email of the user
        String code=emailGenerator.userEmailValidationCodeGenerator();
        mailSender("applicationtesting1604@gmail.com,userRequest.getEmail()",userRequest.getEmail(), code, "Email Validation Code");
        if(!mailValidation(code)){
            throw new InValidEmailVerificationCodeException("Invalid code. Email cannot be verified");
        }

        User user= UserTransformer.userRequestToUser(userRequest);
        Card card=cardServiceImpl.createCard(user);

        //saves both user and card automatically because of cascading function
        User savedUser=userRepository.save(user);
        return "User "+savedUser.getUserName()+" has been registered successfully";
    }

    private void mailSender(String senderEmail, String recipientEmail, String body, String subject){
        SimpleMailMessage mailMessage=new SimpleMailMessage();
        mailMessage.setFrom(senderEmail);
        mailMessage.setTo(recipientEmail);
        mailMessage.setSubject(subject);
        mailMessage.setText(body);
        mailSender.send(mailMessage);
    }
    private boolean mailValidation(String code){
        if(this.code.equals(code))return true;
        else return false;
    }
    protected User findUserByUserCode(String userCode)throws Exception{
        Optional<User>optionalUser=userRepository.findByUserCode(userCode);
        if(!optionalUser.isPresent()){
            throw new UserNotFoundException("User with the particular user code "+userCode+" is not present in the database");
        }
        return optionalUser.get();
    }
}
