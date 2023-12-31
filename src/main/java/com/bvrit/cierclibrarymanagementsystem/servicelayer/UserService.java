package com.bvrit.cierclibrarymanagementsystem.servicelayer;

import com.bvrit.cierclibrarymanagementsystem.dtos.requestdtos.UserEmailRequest;
import com.bvrit.cierclibrarymanagementsystem.dtos.requestdtos.UserRequest;
import com.bvrit.cierclibrarymanagementsystem.dtos.responsedtos.UserResponse;
import com.bvrit.cierclibrarymanagementsystem.enums.Role;
import com.bvrit.cierclibrarymanagementsystem.exceptions.UserNotFoundException;
import com.bvrit.cierclibrarymanagementsystem.models.User;

import java.util.List;


public interface UserService {
    public String sendEmailValidationCode(UserEmailRequest userEmailRequest) throws Exception;
    public String addUser(UserRequest userRequest, Role role)throws Exception;
    public String deleteUsersByUserCodeList(List<String> userCodeList)throws Exception;
    public UserResponse getUserByUserCode(String userCode) throws UserNotFoundException;
    public UserResponse getUserByUserEmail(String userEmail) throws UserNotFoundException;
    private void mailSender(String senderEmail, String recipientEmail, String body, String subject){

    }
    private boolean mailValidation(String code) {
    return true;
    }
    User findUserByUserCode(String userCode)throws Exception;
}
