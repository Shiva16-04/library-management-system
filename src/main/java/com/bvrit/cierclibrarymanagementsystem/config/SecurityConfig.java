package com.bvrit.cierclibrarymanagementsystem.config;

import com.bvrit.cierclibrarymanagementsystem.models.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @SuppressWarnings("removal")
    @Bean
    public SecurityFilterChain getSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/user/email-authentication-code-to-user-email", "/admin/add-admin-ci-erc-library-management-system", "/user/add-user", "/admin/top-book-list-by-new-york-times").permitAll()
                .requestMatchers("/admin/add-utility-details", "/admin/get-utility-details-list", "/utility/**").hasAnyRole("ADMIN")
                .requestMatchers( "/user/find-user-by-user-code/{userCode}", "/user/find-user-by-user-email", "/user/delete-user-by-user-code").hasRole("ADMIN")
                .requestMatchers("/author/add-author", "/author/delete-author-by-author-code", "/book/add-book", "/book/update-books-status", "/book/delete-book").hasRole("ADMIN")
                .requestMatchers("/issue-return/**", "/transaction/**").hasRole("ADMIN")
                .requestMatchers("/book/get-book-by-book-code/{bookCode}", "/book/get-book-list-by-book-name", "/book/get-book-list-by-genreEnum", "/book/get-book-list-by-book-status").hasAnyRole("ADMIN", "STUDENT")
                .requestMatchers("/author/find-author-by-author-code", "/author/find-author-by-author-email", "/author/get-book-list-by-author-code").hasAnyRole("ADMIN", "STUDENT")
                .anyRequest()
                .authenticated()
                .and()
                .formLogin();
        return httpSecurity.build();
    }
    @Bean
    public BCryptPasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public UserDetailsService getUserDetailsService(){
        return new CustomUserDetailsService();
    }
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(getUserDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(getPasswordEncoder());
        return daoAuthenticationProvider;
    }

}
