package com.example.webbanghang.model.response;

public class CheckoutResponse {
    private String payURL;
    public CheckoutResponse() {

    } 
    public CheckoutResponse(String payURL) {
        this.payURL= payURL;

    }
    public String getPayURL() {
        return this.payURL;
    } 
    public void setPayURL(String payURL) {
        this.payURL= payURL;
    }
}
