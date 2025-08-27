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
@Table(name="VERIFICATION_CODES")
public class VerificationCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private long id;
    @ManyToOne
    @JoinColumn(name="user_id") 
    private User user;
    private Date createAt;
    private Date expireAt;
    private long token;
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public Date getCreateAt() {
        return createAt;
    }
    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
    public Date getExpireAt() {
        return expireAt;
    }
    public void setExpireAt(Date expireAt) {
        this.expireAt = expireAt;
    }
    public long getToken() {
        return token;
    }
    public void setToken(long token) {
        this.token = token;
    }
    public VerificationCode() {
    }
    public VerificationCode(long id, User user, Date createAt, Date expireAt, long token) {
        this.id = id;
        this.user = user;
        this.createAt = createAt;
        this.expireAt = expireAt;
        this.token = token;
    } 
    
}
