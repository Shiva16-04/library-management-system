package com.bvrit.cierclibrarymanagementsystem.controllerlayer;

import com.bvrit.cierclibrarymanagementsystem.dtos.requestdtos.UserEmailRequest;
import com.bvrit.cierclibrarymanagementsystem.dtos.requestdtos.UserRequest;
import com.bvrit.cierclibrarymanagementsystem.enums.Role;
import com.bvrit.cierclibrarymanagementsystem.exceptions.UserNotFoundException;
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

    @PostMapping("/email-authentication-code-to-user-email")
    public ResponseEntity sendEmailValidationCode(@RequestBody UserEmailRequest userEmailRequest){
      try {
          System.out.println("Hello ");
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

    @GetMapping("/find-user-by-user-code/{userCode}")
    public ResponseEntity getUserByUserCode(@PathVariable ("userCode") String userCode){
        try {
            return new ResponseEntity<>(userService.getUserByUserCode(userCode), HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity(e.fillInStackTrace(), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/find-user-by-user-email")
    public ResponseEntity getUserByUserEmail(@RequestParam String userEmail){
        try {
            return new ResponseEntity<>(userService.getUserByUserEmail(userEmail), HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity(e.fillInStackTrace(), HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/delete-user-by-user-code")
    public ResponseEntity deleteUsersByUserCodeList(@RequestParam  List<String> userCodeList){
        try {
            return new ResponseEntity(userService.deleteUsersByUserCodeList(userCodeList),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
