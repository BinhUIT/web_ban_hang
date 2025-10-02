package com.example.webbanghang.middleware;

import java.util.regex.Pattern;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.example.webbanghang.exception.BadRequestException;
import com.example.webbanghang.exception.ConflictException;
import com.example.webbanghang.model.request.RegisterRequest;
import com.example.webbanghang.service.UserService;

@Component
public class Validator {
    private final UserService userService;
    private final String email_regex="^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
            "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private final String phone_regex = "^(03|05|07|08|09)\\\\d{8}$";
    public Validator(UserService userService) {
        this.userService = userService;
    } 

    public void validateRegisterRequest(RegisterRequest request) throws RuntimeException {
        if(!isValidString(request.getName())) {
            throw new BadRequestException("Name can not be empty");
        }
        if(!isValidString(request.getAddress())) {
            throw new BadRequestException("Address can not be empty");
        }
        if(!validatePassword(request.getPassword())) {
            throw new BadRequestException("Password must be at least 8 characters");
        }
        if(!validateEmail(request.getEmail())) {
            throw new BadRequestException("Invalid email");
        }
        if(!request.getConfirmPassword().equals(request.getPassword())) {
            throw new BadRequestException("Password and confirm password does not match");
        }
        try {
            UserDetails userDetails= userService.loadUserByUsername(request.getEmail());
            throw new ConflictException("This email has already be used");
        }
        catch(Exception e) {
            return;
        }
    }
    public boolean validateEmail(String email) {
        if(!isValidString(email)) {
            return false;
        }
        Pattern emailPattern = Pattern.compile(email_regex);
        return emailPattern.matcher(email).matches();
    }
    public boolean validatePhone(String phone) {
        if(!isValidString(phone)) {
            return false;
        } 
        Pattern phonePattern = Pattern.compile(phone_regex);
        return phonePattern.matcher(phone).matches();
    }
    public boolean validatePassword(String password) {
        if(!isValidString(password)) {
            return false;
        } 
        return password.length()>=8;
    }
    public boolean isValidString(String str) {
        return !(str==null||str.equals(""));
    }
}
