package com.bvrit.cierclibrarymanagementsystem.servicelayer;

import com.bvrit.cierclibrarymanagementsystem.dtos.requestdtos.UserEmailRequest;
import com.bvrit.cierclibrarymanagementsystem.dtos.requestdtos.UserRequest;
import com.bvrit.cierclibrarymanagementsystem.models.User;

public interface UserService {
    public String addUser(UserRequest userRequest)throws Exception;
    public void sendEmailValidationCode(UserEmailRequest userEmailRequest);
    private void mailSender(String senderEmail, String recipientEmail, String body, String subject){

    }
    private boolean mailValidation(String code) {
    return true;
    }
    User findUserByUserCode(String userCode)throws Exception;
}
