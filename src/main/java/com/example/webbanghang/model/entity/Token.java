package com.example.webbanghang.model.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="TOKEN")
public class Token {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY) 
    private int id;
    private String token;
    private Date addAt;

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

    public Token() {
        this.addAt= new Date();
    }

    public Token(int id, String token, Date addAt) {
        this.id = id;
        this.token = token;
        this.addAt= addAt;
    }

    public Date getAddAt() {
        return addAt;
    }

    public void setAddAt(Date addAt) {
        this.addAt = addAt;
    }
    
}
