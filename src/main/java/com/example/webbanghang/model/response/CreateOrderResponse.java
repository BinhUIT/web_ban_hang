package com.example.webbanghang.model.response;

public class CreateOrderResponse {
    private int orderId;
    private String message;
    public CreateOrderResponse() {
         
    } 
    public CreateOrderResponse(int orderId, String message) {
        this.orderId = orderId;
        this.message = message;
    } 
    public int getOrderId() {
        return this.orderId;
    } 
    public String getMessage() {
        return this.message;
    } 
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    } 
    public void setMessage(String message) {
        this.message= message;
    }

}
