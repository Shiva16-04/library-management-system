package com.bvrit.cierclibrarymanagementsystem.config;

import com.bvrit.cierclibrarymanagementsystem.models.User;
import com.bvrit.cierclibrarymanagementsystem.repositorylayer.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String userCode) throws UsernameNotFoundException {
        Optional<User> optionalUser=userRepository.findUserByUserCode(userCode);
        if(!optionalUser.isPresent()) {
            throw new UsernameNotFoundException("Invalid Username/code");
        }
        User user=optionalUser.get();
        return new UserDetailsCreator(user);
    }
}
