package com.example.webbanghang.service;


import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.webbanghang.middleware.Constants;
import com.example.webbanghang.model.entity.User;
import com.example.webbanghang.model.request.LoginRequest;
import com.example.webbanghang.model.response.LoginResponse;
import com.example.webbanghang.repository.UserRepository;
@Service
public class UserService implements UserDetailsService {
    private UserRepository userRepo;
    private ApplicationContext applicationContext;
    
    public UserService(UserRepository userRepo, ApplicationContext applicationContext) {
        this.userRepo= userRepo;
        this.applicationContext= applicationContext;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(username);
        if(user==null) {
            throw new UsernameNotFoundException("User not found");
        } 
        return user;
    }
    public LoginResponse login(LoginRequest request) {
        AuthenticationManager authManager = applicationContext.getBean(AuthenticationManager.class);
        Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        if(auth.isAuthenticated()) {
            Object userPrincipal = auth.getPrincipal();
            if(userPrincipal instanceof User user) {
                return new LoginResponse(user, "", new Date(System.currentTimeMillis()+Constants.tokenExpireTime*60*1000));
            }
            return null;
        }
        return null;
    }

}
