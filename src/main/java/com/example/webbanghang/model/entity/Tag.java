package com.example.webbanghang.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="TAGS")
public class Tag {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY) 
    private int id;
    private String name;
    private boolean isEnable;

    public Tag() {
    }

    public Tag(int id, boolean isEnable, String name) {
        this.id = id;
        this.isEnable = isEnable;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isIsEnable() {
        return isEnable;
    }

    public void setIsEnable(boolean isEnable) {
        this.isEnable = isEnable;
    }
    
}
