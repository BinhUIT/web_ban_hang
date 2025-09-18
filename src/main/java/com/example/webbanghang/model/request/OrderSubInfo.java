package com.example.webbanghang.model.request;

public class OrderSubInfo {
    
    private String address;
    private String phone;
    public OrderSubInfo() {

    } 
    public OrderSubInfo( String address, String phone){
        
        this.address = address;
        this.phone= phone;
    } 
     
    public String getAddress(){
        return this.address;
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
