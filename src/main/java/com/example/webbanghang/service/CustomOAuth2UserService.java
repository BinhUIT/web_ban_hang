package com.example.webbanghang.service;

import java.util.Date;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.example.webbanghang.model.entity.Role;
import com.example.webbanghang.model.entity.User;
import com.example.webbanghang.model.enums.EAuthProvider;
import com.example.webbanghang.repository.RoleRepository;
import com.example.webbanghang.repository.UserRepository;
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    public CustomOAuth2UserService(UserRepository userRepo, RoleRepository roleRepo) {
        this.roleRepo=roleRepo;
        this.userRepo= userRepo;
    }  
    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(request);
        String email = user.getAttribute("email");
        String name = user.getAttribute("name"); 
        User userFromDB= userRepo.findByEmail(email);
        if(userFromDB==null) {
            userFromDB = new User();
            userFromDB.setName(name); 
            userFromDB.setEmail(email); 
            userFromDB.setPassword("");
            userFromDB.setActive(true); 
            userFromDB.setAuthProvider(EAuthProvider.GOOGLE); 
            userFromDB.setCreateAt(new Date()); 
            userFromDB.setCart(null); 
            userFromDB.setUpdateAt(null); 
            
            Role role = roleRepo.findById(1).orElse(null);
            userFromDB.setRole(role);
            
            userRepo.save(userFromDB);
        }
        return user;
    }
}
