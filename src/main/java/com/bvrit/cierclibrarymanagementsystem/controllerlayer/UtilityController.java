package com.bvrit.cierclibrarymanagementsystem.controllerlayer;


import com.bvrit.cierclibrarymanagementsystem.servicelayer.impl.AuthenticationDetailsServiceImpl;
import com.bvrit.cierclibrarymanagementsystem.dtos.requestdtos.UtilityRequest;
import com.bvrit.cierclibrarymanagementsystem.servicelayer.UtilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/utility")
public class UtilityController {
    @Autowired
    private UtilityService utilityService;
    @Autowired
    private AuthenticationDetailsServiceImpl authenticationDetailsService;
    @PostMapping("/add-utility-details")
    public ResponseEntity addUtilityDetails(@RequestBody UtilityRequest utilityRequest){
        try {
            return new ResponseEntity<>(utilityService.addUtilityDetails(utilityRequest), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/get-utility-details-list")
    public ResponseEntity getUtilityDetailsList(){
        return new ResponseEntity(utilityService.getUtilityDetailsList(), HttpStatus.OK);
    }
    @PostMapping("/configure-utility-details-utility-code")
    public ResponseEntity configureUtilityDetailsByUtilityCode(@RequestParam String utilityCode){
        try {
            return new ResponseEntity(utilityService.configureUtilityDetailsByUtilityCode(utilityCode), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/get-authentication-details")
    public ResponseEntity getAuthenticationDetails(){
        return new ResponseEntity<>(authenticationDetailsService.getAuthenticationDetails(), HttpStatus.OK);
    }

}
