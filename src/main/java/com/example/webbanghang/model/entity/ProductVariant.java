package com.example.webbanghang.model.entity;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="PRODUCT_VARIANTS")
public class ProductVariant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private int id; 
    @ManyToOne
    @JoinColumn(name="product_id") 
    @JsonIgnore
    private Product product;
    private String name;
    private String shortDesc;
    private String detailDesc;
    private Date createAt;
    private Date updateAt;
    private boolean isEnable;
    private long quantity;
    private long sold;
    private float price;
    @OneToMany(mappedBy = "productVariant") 
    private List<ProductAttribute> productAttributes;
    
    public ProductVariant() {
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    @JsonIgnore
    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getShortDesc() {
        return shortDesc;
    }
    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }
    public String getDetailDesc() {
        return detailDesc;
    }
    public void setDetailDesc(String detailDesc) {
        this.detailDesc = detailDesc;
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
    public boolean isEnable() {
        return isEnable;
    }
    public void setEnable(boolean isEnable) {
        this.isEnable = isEnable;
    }
    public long getQuantity() {
        return quantity;
    }
    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }
    public long getSold() {
        return sold;
    }
    public void setSold(long sold) {
        this.sold = sold;
    }
    public float getPrice() {
        return price;
    }
    public void setPrice(float price) {
        this.price = price;
    }
    public List<ProductAttribute> getProductAttributes() {
        return productAttributes;
    }
    public void setProductAttributes(List<ProductAttribute> productAttributes) {
        this.productAttributes = productAttributes;
    }
    public ProductVariant(int id, Product product, String name, String shortDesc, String detailDesc, Date createAt,
            Date updateAt, boolean isEnable, long quantity, long sold, float price,
            List<ProductAttribute> productAttributes) {
        this.id = id;
        this.product = product;
        this.name = name;
        this.shortDesc = shortDesc;
        this.detailDesc = detailDesc;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.isEnable = isEnable;
        this.quantity = quantity;
        this.sold = sold;
        this.price = price;
        this.productAttributes = productAttributes;
    }
    
}
