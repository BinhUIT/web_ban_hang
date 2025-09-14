package com.example.webbanghang.model.request;

public class OrderSubInfo {
    private String email;
    private String address;
    private String phone;
    public OrderSubInfo() {

    } 
    public OrderSubInfo(String email, String address, String phone){
        this.email= email;
        this.address = address;
        this.phone= phone;
    } 
    public String getEmail() {
        return this.email;
    } 
    public String getAddress(){
        return this.address;
    } 
    public void setEmail(String email) {
        this.email = email;
    } 
    public void setAddress(String address) {
        this.address = address;
    }
    public String getPhone() {
        return this.phone;
    } 
    public void setPhone(String phone) {
        this.phone = phone;
    }
}
