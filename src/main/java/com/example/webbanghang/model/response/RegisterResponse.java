package com.example.webbanghang.model.response;

import com.example.webbanghang.model.entity.User;

public class RegisterResponse {
    private long userId;
    private String name;
    private String email;
    private String phone;
    private String address;
    public RegisterResponse() {

    } 
    public RegisterResponse(long userId, String name, String email, String phone, String address) {
        this.userId = userId;
        this.name= name;
        this.email = email;
        this.phone= phone;
        this.address= address;
    }
    public long getUserId() {
        return this.userId;
    } 
    public String getName() {
        return this.name;
    } 
    public String getEmail() {
        return this.email;
    } 
    public String getPhone() {
        return this.phone;
    } 
    public String getAddress() {
        return this.address;
    }
    public void setUserId(long userId) {
        this.userId= userId;
    } 
    public void setName(String name) {
        this.name= name;
    } 
    public void setEmail(String email) {
        this.email = email;
    } 
    public void setPhone(String phone) {
        this.phone = phone;
    } 
    public void setAddress(String address) {
        this.address = address;
    }
    public RegisterResponse(User user) {
        this.userId = user.getId();
        this.name= user.getName();
        this.email= user.getEmail();
        this.phone = user.getPhone();
        this.address= user.getAddress();
    }
}
