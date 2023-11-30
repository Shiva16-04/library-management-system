package com.bvrit.cierclibrarymanagementsystem.servicelayer.impl;

import com.bvrit.cierclibrarymanagementsystem.Transformers.UserTransformer;
import com.bvrit.cierclibrarymanagementsystem.dtos.requestdtos.UserRequest;
import com.bvrit.cierclibrarymanagementsystem.exceptions.UserAlreadyPresentException;
import com.bvrit.cierclibrarymanagementsystem.models.Card;
import com.bvrit.cierclibrarymanagementsystem.models.User;
import com.bvrit.cierclibrarymanagementsystem.repositorylayer.UserRepository;
import com.bvrit.cierclibrarymanagementsystem.servicelayer.CardService;
import com.bvrit.cierclibrarymanagementsystem.servicelayer.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private CardServiceImpl cardServiceImpl;
    @Autowired
    private UserRepository userRepository;

    public String addUser(UserRequest userRequest)throws Exception{
        Optional<User>optionalUser=userRepository.findByEmail(userRequest.getEmail());
        if(optionalUser.isPresent()){
            throw new UserAlreadyPresentException("User already Registered");
        }
        User user= UserTransformer.userRequestToUser(userRequest);
        Card card=cardServiceImpl.createCard(user);

        //saves both user and card automatically because of cascading function
        User savedUser=userRepository.save(user);
        return "User "+savedUser.getUserName()+" has been registered successfully";
    }
}
