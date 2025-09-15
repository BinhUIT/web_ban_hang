package com.example.webbanghang.model.onlinepayment;

public class CreatePayUrlBody {
    private int orderCode;
    private int amount;
    private String description;
    private String cancelUrl;
    private String returnUrl;
    private String signature;
    public CreatePayUrlBody() {

    } 
    public CreatePayUrlBody(int orderCode, int amount, String description, String cancelUrl, String returnUrl, String signature) {
        this.orderCode = orderCode;
        this.amount = amount;
        this.description = description;
        this.cancelUrl = cancelUrl;
        this.returnUrl= returnUrl;
        this.signature= signature;
    } 
    public int getOrderCode() {
        return this.orderCode;
    } 
    public int getAmount() {
        return this.amount;
    } 
    public String getDescription() {
        return this.description;
    } 
    public String getCancelUrl() {
        return this.cancelUrl;
    } 
    public String getReturnUrl() {
        return this.returnUrl;
    } 
    public String getSignature() {
        return this.signature; 
    } 
    public void setOrderCode(int orderCode) {
        this.orderCode= orderCode;
    } 
    public void setAmount(int amount) {
        this.amount = amount;
    } 
    public void setDescription(String description) {
        this.description = description;
    } 
    public void setCancelUrl(String cancelUrl) {
        this.cancelUrl= cancelUrl;
    } 
    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    } 
    public void setSignature(String signature) {
        this.signature = signature;
    }

}
