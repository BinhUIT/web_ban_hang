package com.example.webbanghang.model.request;

public class LinkAccountRequest {
    private String accessToken;
    private String password;
    
    public LinkAccountRequest() {
    }
    public LinkAccountRequest(String accessToken, String password) {
        this.accessToken = accessToken;
        this.password = password;
    }
    public String getAccessToken() {
        return accessToken;
    }
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    
}
