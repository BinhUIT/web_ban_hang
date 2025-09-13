package com.example.webbanghang.model.request;

public class AddToCartRequest {
    private int productVariantId;
    private int amount;
    public AddToCartRequest() {

    } 
    public AddToCartRequest(int productVariantId, int amount) {
        this.productVariantId= productVariantId;
        this.amount= amount;
    } 
    public int getProductVariantId() {
        return this.productVariantId;
    } 
    public int getAmount() {
        return this.amount;
    } 
    public void setProductVariantId(int productVariantId) {
        this.productVariantId = productVariantId;
    } 
    public void setAmount(int amount) {
        this.amount=amount;
    }
}
