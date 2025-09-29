package com.example.webbanghang.service.authservice;

import java.util.Date;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.webbanghang.middleware.Constants;
import com.example.webbanghang.model.entity.User;
import com.example.webbanghang.model.request.LoginRequest;
import com.example.webbanghang.model.response.LoginResponse;
import com.example.webbanghang.repository.UserRepository;
import com.example.webbanghang.service.JwtService;

public class AuthService {
    
    
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    public AuthService( JwtService jwtService, AuthenticationManager authenticationManager) {
        
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }
    
    public LoginResponse login(LoginRequest request) {
       
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        if(auth.isAuthenticated()) {
            Object userPrincipal = auth.getPrincipal();
            if(userPrincipal instanceof User user) {
                return new LoginResponse(user, jwtService.generateToken(user.getEmail()), new Date(System.currentTimeMillis()+Constants.tokenExpireTime*60*1000));
            }
            return null;
        }
        return null;
    }


}
