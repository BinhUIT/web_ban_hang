package com.example.webbanghang.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.webbanghang.model.request.LoginRequest;
import com.example.webbanghang.model.response.LoginResponse;
import com.example.webbanghang.service.UserService;

@RestController
public class UserController {
    private UserService userService;
    public UserController(UserService userService) {
        this.userService= userService;
    }
    @GetMapping("/unsecure/test") 
    public String test() {
        return "Hello";
    }
    @GetMapping("/test/token") 
    public String testWithToken() {
        return "Success";
    }
    @PostMapping("/unsecure/login") 
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginResponse response = userService.login(request);
        if(response==null) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } 
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
