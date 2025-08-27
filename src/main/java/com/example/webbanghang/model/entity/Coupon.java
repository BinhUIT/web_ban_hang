package com.example.webbanghang.model.entity;

import java.util.Date;

import com.example.webbanghang.model.enums.EDiscountType;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="COUPONS")
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private int id;
    EDiscountType discountType;
    @ManyToOne
    @JoinColumn(name="product_id") 
    private Product product;
    private float discount;
    private Date createAt;
    private Date updateAt;
    private Date startAt;
    private Date endAt;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public EDiscountType getDiscountType() {
        return discountType;
    }
    public void setDiscountType(EDiscountType discountType) {
        this.discountType = discountType;
    }
    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
    }
    public float getDiscount() {
        return discount;
    }
    public void setDiscount(float discount) {
        this.discount = discount;
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
    public Date getStartAt() {
        return startAt;
    }
    public void setStartAt(Date startAt) {
        this.startAt = startAt;
    }
    public Date getEndAt() {
        return endAt;
    }
    public void setEndAt(Date endAt) {
        this.endAt = endAt;
    }
    public Coupon() {
    }
    public Coupon(int id, EDiscountType discountType, Product product, float discount, Date createAt, Date updateAt,
            Date startAt, Date endAt) {
        this.id = id;
        this.discountType = discountType;
        this.product = product;
        this.discount = discount;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.startAt = startAt;
        this.endAt = endAt;
    }
    
}
