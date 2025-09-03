package com.example.webbanghang.model.entity;

import com.example.webbanghang.model.enums.EProductSize;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="PRODUCT_SIZE")
public class ProductSize {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY) 
    private int id;
    private EProductSize productSize;
    public ProductSize(){

    } 
    public ProductSize(int id, EProductSize productSize) {
        this.id = id;
        this.productSize= productSize;
    } 
    public int getId() {
        return this.id;
    } 
    public EProductSize getProductSize() {
        return this.productSize;
    }
}
