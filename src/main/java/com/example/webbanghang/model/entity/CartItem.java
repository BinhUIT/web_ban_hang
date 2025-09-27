package com.example.webbanghang.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Table;


@Table(name="CART_ITEMS")
public class CartItem {
   
    private int id;
     
    private ProductVariant productVariant;
     
    @JsonIgnore
    private Cart cart;
    private int amount;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public ProductVariant getProductVariant() {
        return productVariant;
    }
    public void setProductVariant(ProductVariant productVariant) {
        this.productVariant = productVariant;
    }
    @JsonIgnore
    public Cart getCart() {
        return cart;
    }
    public void setCart(Cart cart) {
        this.cart = cart;
    }
    public int getAmount() {
        return amount;
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }
    public CartItem() {
    }
    public CartItem(int id, ProductVariant productVariant, Cart cart, int amount) {
        this.id = id;
        this.productVariant = productVariant;
        this.cart = cart;
        this.amount = amount;
    }
    
}
