package com.bvrit.cierclibrarymanagementsystem.controllerlayer;

import com.bvrit.cierclibrarymanagementsystem.dtos.requestdtos.UserRequest;
import com.bvrit.cierclibrarymanagementsystem.dtos.requestdtos.UtilityRequest;
import com.bvrit.cierclibrarymanagementsystem.enums.Role;
import com.bvrit.cierclibrarymanagementsystem.servicelayer.AdminService;
import com.bvrit.cierclibrarymanagementsystem.servicelayer.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;
    @Autowired
    private AdminService adminService;

    @PostMapping("/add-admin-ci-erc-library-management-system")
    public ResponseEntity addAdmin(@RequestBody UserRequest userRequest){
        try {
            return new ResponseEntity<>(userService.addUser(userRequest, Role.ADMIN), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
