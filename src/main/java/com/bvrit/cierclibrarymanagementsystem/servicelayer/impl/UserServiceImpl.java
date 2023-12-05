package com.bvrit.cierclibrarymanagementsystem.servicelayer.impl;

import com.bvrit.cierclibrarymanagementsystem.Transformers.UserTransformer;
import com.bvrit.cierclibrarymanagementsystem.dtos.requestdtos.UserEmailRequest;
import com.bvrit.cierclibrarymanagementsystem.dtos.requestdtos.UserEmailVerificationCodeRequest;
import com.bvrit.cierclibrarymanagementsystem.dtos.requestdtos.UserRequest;
import com.bvrit.cierclibrarymanagementsystem.dtos.responsedtos.UserResponse;
import com.bvrit.cierclibrarymanagementsystem.exceptions.InValidEmailVerificationCodeException;
import com.bvrit.cierclibrarymanagementsystem.exceptions.UserAlreadyPresentException;
import com.bvrit.cierclibrarymanagementsystem.exceptions.UserNotFoundException;
import com.bvrit.cierclibrarymanagementsystem.generators.EmailGenerator;
import com.bvrit.cierclibrarymanagementsystem.models.Card;
import com.bvrit.cierclibrarymanagementsystem.models.User;
import com.bvrit.cierclibrarymanagementsystem.models.UserEmailVerificationCode;
import com.bvrit.cierclibrarymanagementsystem.repositorylayer.UserEmailVerificationCodeRepository;
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
    private CardService cardService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private EmailGenerator emailGenerator;
    @Autowired
    private UserEmailVerificationCodeServiceImpl userEmailVerificationCodeService;
    @Autowired
    private UserEmailVerificationCodeRepository userEmailVerificationCodeRepository;

    public String addUser(UserRequest userRequest)throws Exception{
        Optional<User>optionalUser=userRepository.findUserByEmail(userRequest.getEmail());
        if(optionalUser.isPresent()){
            throw new UserAlreadyPresentException("User already Registered");
        }
        //validating the email of the user
        String codeFromUser=userRequest.getEmailVerificationCode();
        mailValidation(userRequest.getEmail(), codeFromUser);

        User user= UserTransformer.userRequestToUser(userRequest);
        Card card= cardService.createCard(user);

        //saves both user and card automatically because of cascading function
        User savedUser=userRepository.save(user);
        return "User "+savedUser.getUserName()+" has been registered successfully";
    }
    public String sendEmailValidationCode(UserEmailRequest userEmailRequest)throws Exception{
        String email=userEmailRequest.getEmail();
        String code=emailGenerator.userEmailValidationCodeGenerator();
        Optional<UserEmailVerificationCode> optionalUserEmailVerificationCode=userEmailVerificationCodeService.findUserEmailVerificationCode(email);

        if(optionalUserEmailVerificationCode.isPresent()){
            UserEmailVerificationCode userEmailVerificationCode=optionalUserEmailVerificationCode.get();
            userEmailVerificationCode.setVerificationCode(code);
            userEmailVerificationCodeRepository.save(userEmailVerificationCode);
        }else{
            userEmailVerificationCodeService.addUserEmailVerificationCode(new UserEmailVerificationCodeRequest(email, code));
        }
        mailSender("applicationtesting1604@gmail.com",email, code, "Email Validation Code");
        return "Verification code sent successfully to the mail"+email;
    }

    private void mailSender(String senderEmail, String recipientEmail, String body, String subject){
        SimpleMailMessage mailMessage=new SimpleMailMessage();
        mailMessage.setFrom(senderEmail);
        mailMessage.setTo(recipientEmail);
        mailMessage.setSubject(subject);
        mailMessage.setText(body);
        mailSender.send(mailMessage);
    }
    private boolean mailValidation(String email, String code)throws Exception{
        Optional<UserEmailVerificationCode> optionalUserEmailVerificationCode=userEmailVerificationCodeService.findUserEmailVerificationCode(email);
        if(optionalUserEmailVerificationCode.isPresent()){
            String userEmailCode=optionalUserEmailVerificationCode.get().getVerificationCode();
            if(userEmailCode.equals(code))return true;
            else throw  new InValidEmailVerificationCodeException("Invalid Code");
        }else{
            throw  new InValidEmailVerificationCodeException("Invalid Code");
        }
    }
    public UserResponse getUserByUserCode(String userCode) throws UserNotFoundException {
        Optional<User>optionalUser=userRepository.findUserByUserCode(userCode);
        if(!optionalUser.isPresent()){
            throw new UserNotFoundException("User with the particular user code "+userCode+" is not present in the database");
        }
        User user=optionalUser.get();
        UserResponse userResponse=UserTransformer.userToUserResponse(user);
        return userResponse;
    }
    public UserResponse getUserByUserEmail(String userEmail) throws UserNotFoundException {
        Optional<User>optionalUser=userRepository.findUserByEmail(userEmail);
        if(!optionalUser.isPresent()){
            throw new UserNotFoundException("User with the particular user email "+userEmail+" is not present in the database");
        }
        User user=optionalUser.get();
        UserResponse userResponse=UserTransformer.userToUserResponse(user);
        return userResponse;
    }
    public User findUserByUserCode(String userCode)throws Exception{
        Optional<User>optionalUser=userRepository.findUserByUserCode(userCode);
        if(!optionalUser.isPresent()){
            throw new UserNotFoundException("User with the particular user code "+userCode+" is not present in the database");
        }
        return optionalUser.get();
    }

}
