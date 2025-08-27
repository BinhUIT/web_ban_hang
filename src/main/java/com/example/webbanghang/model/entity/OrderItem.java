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
@Table(name="ORDER_ITEMS")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private int id;
    @ManyToOne
    @JoinColumn(name="product_variant_id") 
    private ProductVariant productVariant;
    @ManyToOne
    @JoinColumn(name="order_id") 
    @JsonIgnore
    private Order order;
    private int amount;
    private float totalPrice;
    public OrderItem(int id, ProductVariant productVariant, Order order, int amount, float totalPrice) {
        this.id = id;
        this.productVariant = productVariant;
        this.order = order;
        this.amount = amount;
        this.totalPrice = totalPrice;
    }
    public OrderItem() {
    }
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
    public Order getOrder() {
        return order;
    }
    public void setOrder(Order order) {
        this.order = order;
    }
    public int getAmount() {
        return amount;
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }
    public float getTotalPrice() {
        return totalPrice;
    }
    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    } 

}
