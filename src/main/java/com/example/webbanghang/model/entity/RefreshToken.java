package com.example.webbanghang.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="REFRESH_TOKEN")
public class RefreshToken {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    private String token;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public RefreshToken() {
    }
    public RefreshToken(int id, String token) {
        this.id = id;
        this.token = token;
    }
    
}
