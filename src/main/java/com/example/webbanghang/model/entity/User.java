package com.example.webbanghang.model.entity;

import java.util.Date;

import com.example.webbanghang.model.enums.EAuthProvider;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private long id;
    
    private String name;
    private String email;
    private String password;
    private EAuthProvider authProvider;
    @ManyToOne
    @JoinColumn(name="ROLE_ID") 
    private Role role;
    private Date createAt;
    private Date updateAt;
    private boolean isActive;
    @OneToOne(mappedBy = "user") 
    private Cart cart;
    
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public EAuthProvider getAuthProvider() {
        return authProvider;
    }
    public void setAuthProvider(EAuthProvider authProvider) {
        this.authProvider = authProvider;
    }
    public Role getRole() {
        return role;
    }
    public void setRole(Role role) {
        this.role = role;
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
    public boolean isActive() {
        return isActive;
    }
    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }
    public Cart getCart() {
        return cart;
    }
    public void setCart(Cart cart) {
        this.cart = cart;
    }
    public User(long id, String name, String email, String password, EAuthProvider authProvider, Role role,
            Date createAt, Date updateAt, boolean isActive, Cart cart) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.authProvider = authProvider;
        this.role = role;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.isActive = isActive;
        this.cart = cart;
    }
    public User() {
    } 
    
}
