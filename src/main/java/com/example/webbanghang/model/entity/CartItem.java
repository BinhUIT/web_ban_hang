package com.example.webbanghang.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="CART_ITEMS")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private int id;
    @ManyToOne
    @JoinColumn(name = "product_variant_id") 
    private ProductVariant productVariant;
    @ManyToOne
    @JoinColumn(name="cart_id") 
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
