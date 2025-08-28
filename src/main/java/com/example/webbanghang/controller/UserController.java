package com.example.webbanghang.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.example.webbanghang.model.request.LoginRequest;
import com.example.webbanghang.model.response.LoginResponse;
import com.example.webbanghang.service.OAuth2Service;
import com.example.webbanghang.service.UserService;

@RestController
public class UserController {
    private final UserService userService; 
    private final OAuth2Service oAuth2Service;
    public UserController(UserService userService, OAuth2Service oAuth2Service) {
        this.userService= userService;
        this.oAuth2Service= oAuth2Service;
    }
    @GetMapping("/unsecure/test") 
    public String test() {
        return "Hello";
    }
    @GetMapping("/test/token") 
    public String testWithToken() {
        return "Success";
    }
    @GetMapping("/admin/test") 
    public String testAdmin() {
        return "Admin";
    } 
    @PostMapping("/unsecure/login") 
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginResponse response = userService.login(request);
        if(response==null) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } 
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping("/unsecure/login_google") 
    public ResponseEntity<LoginResponse> loginViaGoogle(@RequestBody Map<String,String> tokenJSON) {
        String token = tokenJSON.get("token");
        Map<String,Object> result = oAuth2Service.verifyGGTokenAndGenerateToken(token);
        Object result_code = result.get("Status code");
        if(result_code instanceof Integer resultCode) {
            if(resultCode==401) {
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            } 
            if(resultCode==419) {
                return new ResponseEntity<>(null, HttpStatus.valueOf(419));
            } 
            Object response = result.get("Data");
            if(response instanceof LoginResponse res) {
                return new ResponseEntity<>(res, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
    }
    

}
