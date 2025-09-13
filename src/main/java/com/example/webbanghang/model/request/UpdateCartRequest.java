package com.example.webbanghang.model.request;



public class UpdateCartRequest {
    private int cartItemId;
    private int newAmount;
    public UpdateCartRequest() {

    } 
    public UpdateCartRequest(int cartItemId, int newAmount) {
        this.cartItemId= cartItemId;
        this.newAmount= newAmount;
    }
    public int getCartItemId() {
        return this.cartItemId;
    } 
    public int getNewAmount() {
        return this.newAmount;
    } 
    public void setCartItemId(int cartItemId) {
        this.cartItemId= cartItemId;
    } 
    public void setNewAmount(int newAmount) {
        this.newAmount= newAmount;
    }
}
