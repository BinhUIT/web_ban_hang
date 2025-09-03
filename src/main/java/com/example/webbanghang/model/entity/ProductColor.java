package com.example.webbanghang.model.entity;

import com.example.webbanghang.model.enums.EColor;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="PRODUCT_COLORS")
public class ProductColor {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY) 
    private int id;
    @Enumerated(EnumType.ORDINAL)
    private EColor color;
    public ProductColor() {

    } 
    public ProductColor(int id, EColor color) {
        this.id = id;
        this.color=color;
    } 
    public int getId() {
        return this.id;
    } 
    public EColor getColor() {
        return this.color;
    } 
    public void setColor(EColor color) {
        this.color= color;
    } 
    public void setId(int id) {
        this.id = id;
    }
}
