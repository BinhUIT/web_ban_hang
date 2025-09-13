package com.example.webbanghang.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="TAG_PRODUCTS")
public class TagProducts {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY) 
    private int id;
    @ManyToOne
    @JoinColumn(name="TAG_ID") 
    private Tag tag;
    @ManyToOne
    @JoinColumn(name="PRODUCT_ID") 
    private Product product;

    public TagProducts() {
    }

    public TagProducts(int id, Product product, Tag tag) {
        this.id = id;
        this.product = product;
        this.tag = tag;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
    
}
