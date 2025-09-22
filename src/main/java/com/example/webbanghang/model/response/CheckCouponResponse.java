package com.example.webbanghang.model.response;

import com.example.webbanghang.model.entity.Coupon;

public class CheckCouponResponse {
    private String message;
    private Coupon coupon;
    public CheckCouponResponse() {

    } 
    public CheckCouponResponse(String message, Coupon coupon) {
        this.message= message;
        this.coupon = coupon;
    }
    public String getMessage() {
        return this.message;
    } 
    public Coupon getCoupon() {
        return this.coupon;
    } 
    public void setMessage(String message) {
        this.message = message;
    } 
    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }
}
