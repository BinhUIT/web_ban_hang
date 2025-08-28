package com.example.webbanghang.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.example.webbanghang.middleware.Constants;
import com.example.webbanghang.model.entity.Role;
import com.example.webbanghang.model.entity.User;
import com.example.webbanghang.model.enums.EAuthProvider;
import com.example.webbanghang.model.response.GoogleUserInfo;
import com.example.webbanghang.model.response.LoginResponse;
import com.example.webbanghang.repository.RoleRepository;
import com.example.webbanghang.repository.UserRepository;

@Service
public class OAuth2Service {  
    
    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final JwtService jwtService;
    public OAuth2Service(UserRepository userRepo, RoleRepository roleRepo, JwtService jwtService) {
        this.userRepo= userRepo;
        this.roleRepo= roleRepo;
        this.jwtService= jwtService;
    } 
    public Map<String,Object> verifyGGTokenAndGenerateToken(String token) {
        String url = Constants.ggTokenBaseURL+token;
        try {
            RestTemplate restTemplate= new RestTemplate();
            ResponseEntity<GoogleUserInfo> response = restTemplate.getForEntity(url, GoogleUserInfo.class);
            GoogleUserInfo ggUserInfo = response.getBody();
            User user = userRepo.findByEmail(ggUserInfo.getEmail()); 
            Map<String,Object> res = new HashMap<>();
            if(user!=null) {
                
                if(user.getAuthProvider()==EAuthProvider.DEFAULT) {
                    res.put("Status code", 419);
                    return res;
                } 
                res.put("Status code",200);
                LoginResponse loginResponse = new LoginResponse(user, jwtService.generateToken(user.getEmail()), new Date(System.currentTimeMillis()+Constants.tokenExpireTime+60*1000));
                res.put("Data",loginResponse);
                return res;
            }
            Role role = roleRepo.findById(1).orElse(null);
            user = new User(ggUserInfo, role);
            userRepo.save(user); 
            res.put("Status code",200);
            LoginResponse loginResponse = new LoginResponse(user, jwtService.generateToken(user.getEmail()), new Date(System.currentTimeMillis()+Constants.tokenExpireTime+60*1000));
            res.put("Data",loginResponse);
            return res;
        }
        catch(HttpClientErrorException e) {
            Map<String,Object> res = new HashMap<>();
            res.put("Status code", 401);
            return res;
        }
    }

}
