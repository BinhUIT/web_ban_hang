package com.example.webbanghang.model.redismodel;

public class CartDetail {
    private int productVariantId;
    private int amount;
    public CartDetail() {
    }
    public CartDetail(int productVariantId, int amount) {
        this.productVariantId = productVariantId;
        this.amount = amount;
    }

    public int getProductVariantId() {
        return productVariantId;
    }

    public void setProductVariantId(int productVariantId) {
        this.productVariantId = productVariantId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
    
}
