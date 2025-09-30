package com.example.webbanghang.service.authservice;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.example.webbanghang.middleware.Constants;
import com.example.webbanghang.middleware.PassEncoder;
import com.example.webbanghang.model.entity.Role;
import com.example.webbanghang.model.entity.User;
import com.example.webbanghang.model.enums.EAuthProvider;
import com.example.webbanghang.model.request.LinkAccountRequest;
import com.example.webbanghang.model.response.GoogleUserInfo;
import com.example.webbanghang.model.response.LoginResponse;
import com.example.webbanghang.repository.RoleRepository;
import com.example.webbanghang.repository.UserRepository;
import com.example.webbanghang.service.JwtService;

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
    public GoogleUserInfo getInfoFromToken(String token) throws HttpClientErrorException { 
        String url = Constants.ggTokenBaseURL+token;
        RestTemplate restTemplate= new RestTemplate();
        ResponseEntity<GoogleUserInfo> response = restTemplate.getForEntity(url, GoogleUserInfo.class);
        return response.getBody();
        
    }
    public Map<String,Object> verifyGGTokenAndGenerateToken(String token) { 
        Map<String,Object> res = new HashMap<>();
        try {
            GoogleUserInfo ggUserInfo = getInfoFromToken(token);
            User user = userRepo.findByEmail(ggUserInfo.getEmail()); 
            
            if(user!=null) {
                
                if(user.getAuthProvider()==EAuthProvider.DEFAULT) {
                    res.put("Status code", 419);
                    return res;
                } 
                res.put("Status code",200);
                LoginResponse loginResponse = new LoginResponse(user, jwtService.generateToken(user.getEmail()), new Date(System.currentTimeMillis()+Constants.tokenExpireTime+60*1000), jwtService.generateRefreshToken(user.getEmail()));
                res.put("Data",loginResponse);
                return res;
            }
            Role role = roleRepo.findById(1).orElse(null);
            user = new User(ggUserInfo, role);
            userRepo.save(user); 
            res.put("Status code",200);
            LoginResponse loginResponse = new LoginResponse(user, jwtService.generateToken(user.getEmail()), new Date(System.currentTimeMillis()+Constants.tokenExpireTime+60*1000), jwtService.generateRefreshToken(user.getEmail()));
            res.put("Data",loginResponse);
            return res;
        }
        catch(HttpClientErrorException e) {
            
            res.put("Status code", 401);
            return res;
        }
    }
    public Map<String, Object> linkAccount(LinkAccountRequest request) { 
        Map<String,Object> res = new HashMap<>();
        try {
            GoogleUserInfo googleUserInfo = getInfoFromToken(request.getAccessToken());
            User user = userRepo.findByEmail(googleUserInfo.getEmail());
            if(user==null) {   
            res.put("Status code", 404);
            return res;
            }
            if(user.getAuthProvider()!=EAuthProvider.DEFAULT) {
                res.put("Status code",400);
                return res;
            }
            if(!user.isAccountNonLocked()||!PassEncoder.isPasswordMatch(user.getPassword(), request.getPassword())) {
                res.put("Status code", 401);
                return res;
            }
            user.setAuthProvider(EAuthProvider.GOOGLE); 
            user.setPassword("");
            user.setUpdateAt(new Date());
            userRepo.save(user);
             res.put("Status code",200);
            LoginResponse loginResponse = new LoginResponse(user, jwtService.generateToken(user.getEmail()), new Date(System.currentTimeMillis()+Constants.tokenExpireTime+60*1000), jwtService.generateRefreshToken(user.getEmail()));
            res.put("Data",loginResponse);
            return res;
        } 
        catch(HttpClientErrorException e) {
            res.put("Status code", 401);
            return res;
        }
    }
    

}
