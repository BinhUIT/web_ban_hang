package com.example.webbanghang.model.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="WISHLIST")
public class WishList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private int id;
    @ManyToOne
    @JoinColumn(name="user_id") 
    private User user;
    @ManyToOne
    @JoinColumn(name="product") 
    private Product product;
    private Date createAt;
    private Date updateAt;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
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
    public WishList(int id, User user, Product product, Date createAt, Date updateAt) {
        this.id = id;
        this.user = user;
        this.product = product;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }
    public WishList() {
    }
    
}
