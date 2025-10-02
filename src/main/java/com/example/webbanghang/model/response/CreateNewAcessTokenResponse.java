package com.example.webbanghang.model.response;

import java.util.Date;

public class CreateNewAcessTokenResponse {
    private String token;
    private Date expireAt;
    public CreateNewAcessTokenResponse() {

    } 
    public CreateNewAcessTokenResponse(String token, Date expireAt){
        this.token = token;
        this.expireAt= expireAt;
    } 
    public String getToken() {
        return this.token;
    } 
    public Date getExpireAt() {
        return this.expireAt;
    } 
    public void setToken(String token) {
        this.token= token;
    } 
    public void setExpireAt(Date expireAt) {
        this.expireAt= expireAt;
    }
}
