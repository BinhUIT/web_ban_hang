package com.example.webbanghang.model.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="PRODUCT_ATTRIBUTES")
public class ProductAttribute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private int id; 
    @ManyToOne
    @JoinColumn(name="product_variant_id") 
    @JsonIgnore
    private ProductVariant productVariant;
    private String name;
    private String value;
    private Date create_at;
    private Date update_at;
    private boolean isEnable;
    public ProductAttribute() {
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    @JsonIgnore
    public ProductVariant getProductVariant() {
        return productVariant;
    }
    public void setProductVariant(ProductVariant productVariant) {
        this.productVariant = productVariant;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    public Date getCreate_at() {
        return create_at;
    }
    public void setCreate_at(Date create_at) {
        this.create_at = create_at;
    }
    public Date getUpdate_at() {
        return update_at;
    }
    public void setUpdate_at(Date update_at) {
        this.update_at = update_at;
    }
    public boolean isEnable() {
        return isEnable;
    }
    public void setEnable(boolean isEnable) {
        this.isEnable = isEnable;
    }
    public ProductAttribute(int id, ProductVariant productVariant, String name, String value, Date create_at,
            Date update_at, boolean isEnable) {
        this.id = id;
        this.productVariant = productVariant;
        this.name = name;
        this.value = value;
        this.create_at = create_at;
        this.update_at = update_at;
        this.isEnable = isEnable;
    }
    
}
