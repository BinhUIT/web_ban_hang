package com.example.webbanghang.service;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.webbanghang.model.entity.User;
import com.example.webbanghang.repository.UserRepository;
@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepo;
    
    
    
   
    public UserService(UserRepository userRepo){
        this.userRepo= userRepo;
    }
        
        
        
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(username);
        if(user==null) {
            throw new UsernameNotFoundException("User not found");
        } 
        return user;
    }
    
   
        
        
    
    
    
    
    
    
    
    
}
