package com.bvrit.cierclibrarymanagementsystem.servicelayer.impl;

import com.bvrit.cierclibrarymanagementsystem.Transformers.UserTransformer;
import com.bvrit.cierclibrarymanagementsystem.dtos.requestdtos.UserEmailRequest;
import com.bvrit.cierclibrarymanagementsystem.dtos.requestdtos.UserEmailVerificationCodeRequest;
import com.bvrit.cierclibrarymanagementsystem.dtos.requestdtos.UserRequest;
import com.bvrit.cierclibrarymanagementsystem.dtos.responsedtos.UserResponse;
import com.bvrit.cierclibrarymanagementsystem.enums.BloodGroup;
import com.bvrit.cierclibrarymanagementsystem.enums.CardStatus;
import com.bvrit.cierclibrarymanagementsystem.enums.Role;
import com.bvrit.cierclibrarymanagementsystem.enums.TransactionStatus;
import com.bvrit.cierclibrarymanagementsystem.exceptions.*;
import com.bvrit.cierclibrarymanagementsystem.generators.EmailGenerator;
import com.bvrit.cierclibrarymanagementsystem.models.Card;
import com.bvrit.cierclibrarymanagementsystem.models.User;
import com.bvrit.cierclibrarymanagementsystem.models.UserEmailVerificationCode;
import com.bvrit.cierclibrarymanagementsystem.repositorylayer.UserEmailVerificationCodeRepository;
import com.bvrit.cierclibrarymanagementsystem.repositorylayer.UserRepository;
import com.bvrit.cierclibrarymanagementsystem.servicelayer.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.bvrit.cierclibrarymanagementsystem.servicelayer.impl.MailConfigurationServiceImpl.SENDER_EMAIL;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private CardService cardService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MailConfigurationService mailConfigurationService;
    @Autowired
    private EmailGenerator emailGenerator;
    @Autowired
    private UserEmailVerificationCodeServiceImpl userEmailVerificationCodeService;
    @Autowired
    private UserEmailVerificationCodeRepository userEmailVerificationCodeRepository;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private AuthenticationDetailsService authenticationDetailsService;


    public List<UserResponse> getFilteredUserResponseList(String userCode, String userName, List<Role>roles, BloodGroup bloodGroup,
                                                          String contactNumber, String email, Integer minFineAmount, Integer maxFineAmount,
                                                          List<CardStatus>cardStatuses, Integer numberOfBooksIssued, String bookCode,
                                                          String bookName){
        List<UserResponse>userResponseList=new ArrayList<>();
        List<User>userList=getFilteredUserList(userCode, userName, roles, bloodGroup,
                contactNumber, email, minFineAmount, maxFineAmount, cardStatuses, numberOfBooksIssued,
                bookCode, bookName);

        userResponseList=userListToUserResponseList(userList);
        return userResponseList;
    }
    public List<User> getFilteredUserList(String userCode, String userName, List<Role>roles, BloodGroup bloodGroupEnum,
                                          String contactNumber, String email, Integer minFineAmount, Integer maxFineAmount,
                                          List<CardStatus>cardStatuses, Integer numberOfBooksIssued, String bookCode,
                                          String bookName){
        List<String>roleStringList=new ArrayList<>();
        if(roles!=null && !roles.isEmpty()){
            for(Role role:roles)roleStringList.add(role.toString());
        }else{
            roleStringList=getRoleStringList(roles);
        }
        List<String>cardStausStringList=new ArrayList<>();
        if(cardStatuses!=null && !cardStatuses.isEmpty()){
            for (CardStatus cardStatus: cardStatuses)cardStausStringList.add(cardStatus.toString());
        }else{
            cardStausStringList=getCardStatusStringList(cardStatuses);
        }
        String bloodGroup= bloodGroupEnum!=null?bloodGroupEnum.toString():null;

        return userRepository.getFilteredUserList(userCode, userName, roleStringList, bloodGroup,
                contactNumber, email, minFineAmount, maxFineAmount, cardStausStringList, numberOfBooksIssued,
                bookCode, bookName);
    }
    private List<String> getRoleStringList(List<Role>roleList){
        List<String>roleStringList=new ArrayList<>();
        for(Role role: Role.values()){
            roleStringList.add(role.toString());
        }
        return roleStringList;
    }
    private List<String> getCardStatusStringList(List<CardStatus>cardStatuses){
        List<String>cardStausStringList=new ArrayList<>();
        for(CardStatus cardStatus: CardStatus.values()){
            cardStausStringList.add(cardStatus.toString());
        }
        return cardStausStringList;
    }

    public String addUser(UserRequest userRequest, Role role)throws Exception{
        List<User>userList=getFilteredUserList(null, null, null, null, null, userRequest.getEmail(), null, null, null, null, null, null);
        if(userList.size()>0){
            throw new UserAlreadyPresentException("User already Registered");
        }
        //validating the match of password and retype password
        if(!userRequest.getPassword().equals(userRequest.getReTypePassword())){
            throw new PasswordReTypePasswordNotMatchException("Password and reType password did not match");
        }
        //validating the email of the user
        String codeFromUser=userRequest.getEmailVerificationCode();
        mailValidation(userRequest.getEmail(), codeFromUser);

        User user= UserTransformer.userRequestToUser(userRequest);
        user.setRole(role);
        Card card= cardService.createCard(user);

        //saves both user and card automatically because of cascading function
        User savedUser=userRepository.save(user);

        //initiating transaction creation
        transactionService.createTransaction(TransactionStatus.USER_ADDED, savedUser.getUserCode(), "", "");

        //sending registration confirmation mail to the user
        String emailBody=emailGenerator.userSuccessfulRegistrationMessageEmailGenerator(user.getUserName());
        mailConfigurationService.mailSender(SENDER_EMAIL,user.getEmail(), emailBody, "User Registration Confirmation");

        return "User "+savedUser.getUserName()+" has been registered successfully";
    }
    public String deleteUsersByUserCodeList(List<String> userCodeList)throws Exception{
        List<String>removedUserCodeList=new ArrayList<>();
        List<String>notRemovedUserCodeList=new ArrayList<>();
        for(String userCode: userCodeList){
            User user=getFilteredUserList(userCode, null, null, null, null, null, null, null, null, null, null, null).get(0);
            if(user.getCard().getBookList().size()==0){

                userRepository.deleteById(user.getId());

                //initiating transaction creation
                transactionService.createTransaction(TransactionStatus.USER_REMOVED, userCode, "", authenticationDetailsService.getAuthenticationDetails());

                removedUserCodeList.add(userCode);
            }else notRemovedUserCodeList.add(userCode);
        }
        if(notRemovedUserCodeList.size()==0){
            return "Users "+removedUserCodeList+" removed from the database successfully";
        }
        throw new UserCannotBeRemovedFromDatabaseException("user/users "+notRemovedUserCodeList+" cannot be removed from the database," +
                "because user/users holds books of ERC Club.\n To remove user, book should be returned that is issued");
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
        mailConfigurationService.mailSender("applicationtesting1604@gmail.com",email, code, "Email Validation Code");
        return "Verification code sent successfully to the mail"+email;
    }

    //below methods are used for internal purposes...not for api calling
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
    List<UserResponse> userListToUserResponseList(List<User>userList){
        List<UserResponse>userResponseList=new ArrayList<>();
        for(var user: userList){
            userResponseList.add(UserTransformer.userToUserResponse(user));
        }
        return userResponseList;
    }

}
