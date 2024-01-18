package com.bvrit.cierclibrarymanagementsystem.servicelayer;

import com.bvrit.cierclibrarymanagementsystem.dtos.requestdtos.UserEmailRequest;
import com.bvrit.cierclibrarymanagementsystem.dtos.requestdtos.UserRequest;
import com.bvrit.cierclibrarymanagementsystem.dtos.responsedtos.UserResponse;
import com.bvrit.cierclibrarymanagementsystem.enums.BloodGroup;
import com.bvrit.cierclibrarymanagementsystem.enums.CardStatus;
import com.bvrit.cierclibrarymanagementsystem.enums.Role;
import com.bvrit.cierclibrarymanagementsystem.exceptions.UserNotFoundException;
import com.bvrit.cierclibrarymanagementsystem.models.User;

import java.util.List;


public interface UserService {
    public List<UserResponse> getFilteredUserResponseList(String userCode, String userName, List<Role>roles, BloodGroup bloodGroup,
                                                          String contactNumber, String email, Integer minFineAmount, Integer maxFineAmount,
                                                          List<CardStatus>cardStatuses, Integer numberOfBooksIssued, String bookCode,
                                                          String bookName);
    public List<User> getFilteredUserList(String userCode, String userName, List<Role>roles, BloodGroup bloodGroupEnum,
                                          String contactNumber, String email, Integer minFineAmount, Integer maxFineAmount,
                                          List<CardStatus>cardStatuses, Integer numberOfBooksIssued, String bookCode,
                                          String bookName);
    public String sendEmailValidationCode(UserEmailRequest userEmailRequest) throws Exception;
    public String addUser(UserRequest userRequest, Role role)throws Exception;
    public String deleteUsersByUserCodeList(List<String> userCodeList)throws Exception;

    private void mailSender(String senderEmail, String recipientEmail, String body, String subject){

    }
    private boolean mailValidation(String code) {
    return true;
    }

}
