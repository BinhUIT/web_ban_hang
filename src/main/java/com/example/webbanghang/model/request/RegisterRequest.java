package com.example.webbanghang.model.request;

public class RegisterRequest {
    private String name;
    private String email;
    private String password;
    private String phone;
    private String address;
    private String confirmPassword;
    public RegisterRequest() {

    } 
    public RegisterRequest(String name, String email, String password, String phone, String address, String confirmPassword) {
        this.name= name;
        this.email= email;
        this.password= password;
        this.phone= phone;
        this.address= address;
        this.confirmPassword=confirmPassword;
    } 
    public String getName() {
        return this.name;
    } 
    public String getEmail() {
        return this.email;
    } 
    public String getPassword() {
        return this.password;
    } 
    public String getPhone() {
        return this.phone;
    } 
    public String getAddress() {
        return this.address;
    }
    public void setName(String name) {
        this.name= name;
    } 
    public void setEmail(String email) {
        this.email= email;
    }
    public void setPassword(String password) {
        this.password = password;
    } 
    public String getConfirmPassword() {
        return this.confirmPassword;
    } 
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    } 
    public void setAddress(String address) {
        this.address = address;
    }
}
