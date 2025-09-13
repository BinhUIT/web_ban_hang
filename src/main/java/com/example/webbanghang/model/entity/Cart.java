package com.example.webbanghang.model.entity;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="CART")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private int id;
    @OneToOne(cascade = CascadeType.ALL) 
    @JoinColumn(name="user_id") 
    private User user; 
    private int amount;
    private Date createAt;
    private Date updateAt;
    @OneToMany(mappedBy = "cart") 
    private List<CartItem> cartItems; 
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    @JsonIgnore
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public int getAmount() {
        return amount;
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }
    public Date getCreateAt() {
        return createAt;
    }
    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
    public Date getUpdateAt() {
        return updateAt;
    }
    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }
    public Cart() {
    }
    public List<CartItem> getCartItems() {
        return cartItems;
    }
    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }
    public Cart(int id, User user, int amount, Date createAt, Date updateAt, List<CartItem> cartItems) {
        this.id = id;
        this.user = user;
        this.amount = amount;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.cartItems = cartItems;
    }
    
}
