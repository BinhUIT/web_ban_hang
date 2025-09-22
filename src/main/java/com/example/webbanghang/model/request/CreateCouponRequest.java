package com.example.webbanghang.model.request;

import java.util.Date;

import com.example.webbanghang.model.enums.EDiscountType;
import com.fasterxml.jackson.annotation.JsonFormat;

public class CreateCouponRequest {
    private EDiscountType discountType;
    private int productId;
    private float discount;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private Date startAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private Date endAt;
    private int limit;
    

    public CreateCouponRequest(float discount, EDiscountType discountType, Date endAt, int limit, int productId, Date startAt) {
        this.discount = discount;
        this.discountType = discountType;
        this.endAt = endAt;
        this.limit = limit;
        this.productId = productId;
        this.startAt = startAt;
        
    }

    public CreateCouponRequest() {
    }

    public EDiscountType getDiscountType() {
        return discountType;
    }

    public void setDiscountType(EDiscountType eDiscountType) {
        this.discountType = eDiscountType;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
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

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    
    
}
