package com.bvrit.cierclibrarymanagementsystem.controllerlayer;

import com.bvrit.cierclibrarymanagementsystem.dtos.requestdtos.UserEmailRequest;
import com.bvrit.cierclibrarymanagementsystem.dtos.requestdtos.UserRequest;
import com.bvrit.cierclibrarymanagementsystem.exceptions.UserNotFoundException;
import com.bvrit.cierclibrarymanagementsystem.servicelayer.UserService;
import com.bvrit.cierclibrarymanagementsystem.servicelayer.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/email-authentication-code-to-user-email")
    public ResponseEntity sendEmailValidationCode(@RequestBody UserEmailRequest userEmailRequest){
      try {
          return new ResponseEntity<>(userService.sendEmailValidationCode(userEmailRequest), HttpStatus.OK);
      }catch (Exception e){
          return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
      }
    }

    @PostMapping("/add-user")
    public ResponseEntity addUser(@RequestBody UserRequest userRequest){
        try {
            return new ResponseEntity<>(userService.addUser(userRequest), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/find-user-by-user-code")
    public ResponseEntity getUserByUserCode(@RequestParam String userCode){
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
    public ResponseEntity deleteUserByUserCode(@RequestParam String userCode){
        return new ResponseEntity("",HttpStatus.OK);
    }
}
