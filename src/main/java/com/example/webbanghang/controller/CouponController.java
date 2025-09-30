package com.example.webbanghang.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.webbanghang.middleware.ExceptionHandler;
import com.example.webbanghang.model.entity.Coupon;
import com.example.webbanghang.model.response.Response;
import com.example.webbanghang.service.couponservice.CouponService;

@RestController
public class CouponController {
    private final CouponService couponService;
    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }
    @GetMapping("/unsecure/check_coupon") 
    public ResponseEntity<Response> checkCoupon(@RequestParam String couponCode, @RequestParam float price) {
        System.out.println(couponCode);
        Coupon coupon=couponService.validateCoupon(couponCode, price);
        Response response = new Response("Can use", coupon, 200);
        return new ResponseEntity<>(response, HttpStatus.OK);
        
    } 
}
