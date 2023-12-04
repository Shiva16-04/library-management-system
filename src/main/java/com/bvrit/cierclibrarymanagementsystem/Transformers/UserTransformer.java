package com.bvrit.cierclibrarymanagementsystem.Transformers;

import com.bvrit.cierclibrarymanagementsystem.dtos.requestdtos.UserRequest;
import com.bvrit.cierclibrarymanagementsystem.dtos.responsedtos.UserResponse;
import com.bvrit.cierclibrarymanagementsystem.models.User;

public class UserTransformer {
    public static User userRequestToUser(UserRequest userRequest){
        String email= userRequest.getEmail();
        return User.builder()
                .userName(userRequest.getUserName())
                .userCode(email.substring(0, email.indexOf('@')))
                .bloodGroup(userRequest.getBloodGroup())
                .contactNumber(userRequest.getContactNumber())
                .email(userRequest.getEmail())
                .build();
    }
    public static UserResponse userToUserResponse(User user){
        return UserResponse.builder()
                .userName(user.getUserName())
                .rollNumber(user.getUserCode())
                .bloodGroup(user.getBloodGroup())
                .contactNumber(user.getContactNumber())
                .email(user.getEmail())
                .build();
    }
}
