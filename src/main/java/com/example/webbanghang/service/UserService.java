package com.example.webbanghang.service;


import java.util.Date;


import org.springframework.context.annotation.Lazy;
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
    private final UserRepository userRepo;
    
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    
    public UserService(UserRepository userRepo,  JwtService jwtService, @Lazy AuthenticationManager authenticationManager) {
        this.userRepo= userRepo;
        
        this.jwtService= jwtService;
        this.authenticationManager= authenticationManager;
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
