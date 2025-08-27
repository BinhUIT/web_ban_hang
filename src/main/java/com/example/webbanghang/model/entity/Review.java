package com.example.webbanghang.model.entity;

import java.util.Date;

import com.example.webbanghang.model.enums.EReviewStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="REVIEWS")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private int id;
    @ManyToOne
    @JoinColumn(name="product_id") 
    private Product product;
    @ManyToOne
    @JoinColumn(name="user_id") 
    private User user;
    private int rating;
    private EReviewStatus status;
    private Date createAt;
    private Date updateAt;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public int getRating() {
        return rating;
    }
    public void setRating(int rating) {
        this.rating = rating;
    }
    public EReviewStatus getStatus() {
        return status;
    }
    public void setStatus(EReviewStatus status) {
        this.status = status;
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
    public Review() {
    }
    public Review(int id, Product product, User user, int rating, EReviewStatus status, Date createAt, Date updateAt) {
        this.id = id;
        this.product = product;
        this.user = user;
        this.rating = rating;
        this.status = status;
        this.createAt = createAt;
        this.updateAt = updateAt;
    } 
    
}
