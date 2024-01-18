package com.bvrit.cierclibrarymanagementsystem.config;

import com.bvrit.cierclibrarymanagementsystem.models.User;
import com.bvrit.cierclibrarymanagementsystem.repositorylayer.UserRepository;
import com.bvrit.cierclibrarymanagementsystem.servicelayer.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    UserService userService;
    @Override
    public UserDetails loadUserByUsername(String userCode) throws UsernameNotFoundException {
        List<User> userList=userService.getFilteredUserList(userCode, null, null, null, null, null, null, null, null, null, null, null);
        if(userList.size()==0) {
            throw new UsernameNotFoundException("Invalid Username/code");
        }
        User user=userList.get(0);
        return new UserDetailsCreator(user);
    }
}
