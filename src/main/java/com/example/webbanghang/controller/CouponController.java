package com.example.webbanghang.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.webbanghang.model.response.CheckCouponResponse;
import com.example.webbanghang.service.CouponService;

@RestController
public class CouponController {
    private final CouponService couponService;
    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }
    @GetMapping("/unsecure/check_coupon") 
    public CheckCouponResponse checkCoupon(@RequestParam String couponCode, @RequestParam float price) {
        System.out.println(couponCode);
        return couponService.checkCoupon(couponCode, price);
    } 
}
