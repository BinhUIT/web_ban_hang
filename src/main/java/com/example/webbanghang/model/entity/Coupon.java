package com.example.webbanghang.model.entity;

import java.util.Date;

import com.example.webbanghang.exception.BadRequestException;
import com.example.webbanghang.middleware.UUIDGenerator;
import com.example.webbanghang.model.enums.EDiscountType;
import com.example.webbanghang.model.request.CreateCouponRequest;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name="COUPONS")
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private int id;
    private EDiscountType discountType;
    private float minOrderValue;
    
    private float discount;
    private Date createAt;
    private Date updateAt;
    private Date startAt;
    private Date endAt;
    @Column(name="LIMITS")
    private int limit;//-1 neu ko gioi han so lan su dung
    private int usedTime;
    @Column(nullable = false, unique = true, updatable = false)
    private String code;
    @PrePersist
    public void generateCode() {
        if(this.code ==null) {
            this.code = UUIDGenerator.getRanDomUUID();

        }
    }
    public int getId() {
        return id;
    }
    public boolean getIsEnd() {
        Date currentDate = new Date();
        if(currentDate.before(endAt)) return false;
        return true;
    }
    public boolean getCanUse() {
        return this.limit>this.usedTime;
    }
    public int getLimit() {
        return limit;
    }
    public void setLimit(int limit) {
        this.limit = limit;
    }
    public int getUsedTime() {
        return usedTime;
    }
    public void setUsedTime(int usedTime) {
        this.usedTime = usedTime;
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
            Date startAt, Date endAt, String code, int limit, int usedTime, float minOrderValue) {
        this.id = id;
        this.discountType = discountType;
        this.minOrderValue = minOrderValue;
        this.discount = discount;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.startAt = startAt;
        this.endAt = endAt;
        this.code = code;
        this.limit = limit;
        this.usedTime = usedTime;
    }
    
    public boolean getIsUsable() {
        if(this.startAt.after(new Date())) {
            return false;
        }
        if(this.endAt.before(new Date())) {
            return false;
        }
        if(this.limit!=-1) {
            if(this.usedTime>=this.limit) {
                return false;
            }
        }
        return true;
    }
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    public Coupon(CreateCouponRequest request) throws RuntimeException {
        if(request.getStartAt().after(request.getEndAt())) {
            throw new BadRequestException("Invalid start and end time");
        }
        this.discountType= request.getDiscountType();
        this.createAt= new Date();
        
        this.discount = request.getDiscount();
        this.updateAt= null;
        this.startAt = request.getStartAt();
        this.endAt= request.getEndAt();
        this.limit = request.getLimit();
        this.usedTime= 0;

    }
    public float getMinOrderValue() {
        return minOrderValue;
    }
    public void setMinOrderValue(float minOrderValue) {
        this.minOrderValue = minOrderValue;
    }
    
}
