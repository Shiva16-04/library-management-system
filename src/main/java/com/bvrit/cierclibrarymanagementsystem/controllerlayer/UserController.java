package com.bvrit.cierclibrarymanagementsystem.controllerlayer;

import com.bvrit.cierclibrarymanagementsystem.dtos.requestdtos.UserRequest;
import com.bvrit.cierclibrarymanagementsystem.servicelayer.UserService;
import com.bvrit.cierclibrarymanagementsystem.servicelayer.impl.UserServiceImpl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    private UserServiceImpl userServiceImpl;

    @PostMapping("/addUser")
    public String addUser(@RequestBody UserRequest userRequest){
        return userServiceImpl.addUser(userRequest);
    }

}
