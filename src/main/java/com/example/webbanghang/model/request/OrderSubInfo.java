package com.example.webbanghang.model.request;

import com.example.webbanghang.model.enums.EPaymentType;

public class OrderSubInfo {
    
    private String address;
    private String phone;
    private EPaymentType paymentType;
    public OrderSubInfo() {

    } 
    public OrderSubInfo( String address, String phone, EPaymentType paymentType){
        
        this.address = address;
        this.phone= phone;
        this.paymentType = paymentType;
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
    public EPaymentType getPaymentType() {
        return paymentType;
    }
    public void setPaymentType(EPaymentType paymentType) {
        this.paymentType = paymentType;
    }
}
