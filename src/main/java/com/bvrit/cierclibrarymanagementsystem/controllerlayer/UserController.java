package com.bvrit.cierclibrarymanagementsystem.controllerlayer;

import com.bvrit.cierclibrarymanagementsystem.dtos.requestdtos.UserEmailRequest;
import com.bvrit.cierclibrarymanagementsystem.dtos.requestdtos.UserRequest;
import com.bvrit.cierclibrarymanagementsystem.servicelayer.UserService;
import com.bvrit.cierclibrarymanagementsystem.servicelayer.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/user-email-validation")
    public ResponseEntity sendEmailValidationCode(@RequestBody UserEmailRequest userEmailRequest){
      try {
          return new ResponseEntity<>(userService.sendEmailValidationCode(userEmailRequest), HttpStatus.OK);
      }catch (Exception e){
          return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
      }
    }

    @PostMapping("/addDetails")
    public ResponseEntity addUser(@RequestBody UserRequest userRequest){
        try {
            return new ResponseEntity<>(userService.addUser(userRequest), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


}
