package com.bvrit.cierclibrarymanagementsystem.controllerlayer;

import com.bvrit.cierclibrarymanagementsystem.dtos.requestdtos.UserEmailRequest;
import com.bvrit.cierclibrarymanagementsystem.dtos.requestdtos.UserRequest;
import com.bvrit.cierclibrarymanagementsystem.enums.BloodGroup;
import com.bvrit.cierclibrarymanagementsystem.enums.CardStatus;
import com.bvrit.cierclibrarymanagementsystem.enums.Role;
import com.bvrit.cierclibrarymanagementsystem.exceptions.UserNotFoundException;
import com.bvrit.cierclibrarymanagementsystem.servicelayer.CardService;
import com.bvrit.cierclibrarymanagementsystem.servicelayer.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private CardService cardService;

    @PostMapping("/email-authentication-code-to-user-email")
    public ResponseEntity sendEmailValidationCode(@RequestBody UserEmailRequest userEmailRequest){
      try {
          System.out.println(Role.ADMIN.getDisplayName());
          return new ResponseEntity<>(userService.sendEmailValidationCode(userEmailRequest), HttpStatus.OK);
      }catch (Exception e){
          return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
      }
    }

    @PostMapping("/add-user")
    public ResponseEntity addUser(@RequestBody UserRequest userRequest){
        try {
            return new ResponseEntity<>(userService.addUser(userRequest, Role.STUDENT), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-filtered-user-list")
    public ResponseEntity getFilteredUserList(@RequestParam(required = false) String userCode,
                                              @RequestParam(required = false) String userName,
                                              @RequestParam(required = false) List<Role>roles,
                                              @RequestParam(required = false) BloodGroup bloodGroup,
                                              @RequestParam(required = false) String contactNumber,
                                              @RequestParam(required = false) String email,
                                              @RequestParam(required = false) Integer minFineAmount,
                                              @RequestParam(required = false) Integer maxFineAmount,
                                              @RequestParam(required = false) List<CardStatus>cardStatuses,
                                              @RequestParam(required = false) Integer numberOfBooksIssued,
                                              @RequestParam(required = false) String bookCode,
                                              @RequestParam(required = false) String bookName){
        return new ResponseEntity<>(userService.getFilteredUserResponseList(userCode, userName, roles, bloodGroup,
                contactNumber, email, minFineAmount, maxFineAmount, cardStatuses, numberOfBooksIssued,
                bookCode, bookName), HttpStatus.OK);
    }
    @DeleteMapping("/delete-user-by-user-code")
    public ResponseEntity deleteUsersByUserCodeList(@RequestParam  List<String> userCodeList){
        try {
            return new ResponseEntity(userService.deleteUsersByUserCodeList(userCodeList),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @PatchMapping("/update-card-status")
    public ResponseEntity updateCardStatus(@RequestParam List<String> userCodeList, @RequestParam CardStatus cardStatus){
        try {
            return new ResponseEntity<>(cardService.updateUserCardStatus(userCodeList, cardStatus), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
