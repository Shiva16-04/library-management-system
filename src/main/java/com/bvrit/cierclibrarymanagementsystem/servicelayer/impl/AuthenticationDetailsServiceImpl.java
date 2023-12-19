package com.bvrit.cierclibrarymanagementsystem.servicelayer.impl;

import com.bvrit.cierclibrarymanagementsystem.servicelayer.AuthenticationDetailsService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationDetailsServiceImpl implements AuthenticationDetailsService {
    public String getAuthenticationDetails(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails= (UserDetails)authentication.getPrincipal();
        String userCode= userDetails.getUsername();
        return userCode;
    }
}
