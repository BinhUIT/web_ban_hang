package com.example.webbanghang.model.response;

import java.util.Date;

import com.example.webbanghang.model.entity.User;

public class LoginResponse {
    private User user;
    private String token;
    private Date tokenExpireAt;
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public Date getTokenExpireAt() {
        return tokenExpireAt;
    }
    public void setTokenExpireAt(Date tokenExpireAt) {
        this.tokenExpireAt = tokenExpireAt;
    }
    public LoginResponse() {
    }
    public LoginResponse(User user, String token, Date tokenExpireAt) {
        this.user = user;
        this.token = token;
        this.tokenExpireAt = tokenExpireAt;
    }
    
}
