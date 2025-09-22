package com.example.webbanghang.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.webbanghang.model.entity.Coupon;
import com.example.webbanghang.model.entity.Product;
import com.example.webbanghang.model.request.CreateCouponRequest;
import com.example.webbanghang.model.response.CheckCouponResponse;
import com.example.webbanghang.repository.CouponRepository;
import com.example.webbanghang.repository.ProductRepository;

@Service
public class CouponService {
    private final CouponRepository couponRepo;
    private final ProductRepository productRepo;
    public CouponService(CouponRepository couponRepo, ProductRepository productRepo){
        this.couponRepo = couponRepo;
        this.productRepo= productRepo;
    }
    public List<Coupon> getAllUsableCoupon() {
        List<Coupon> listCouponOfProduct = couponRepo.findAll();
        List<Coupon> result = listCouponOfProduct.stream().filter((item)->{
            return item.getIsUsable();
        }).collect(Collectors.toList());
        return result;
    }
    public Coupon createCoupon(CreateCouponRequest request) throws Exception {
        Product product = productRepo.findById(request.getProductId()).orElse(null);
        if(product==null) {
            throw new Exception("404");
        }
        try {
            Coupon coupon = new Coupon(request);
            coupon=couponRepo.save(coupon);
            return coupon;
        } catch (Exception e) {
            if(e.getMessage().equals("400")){
                throw new Exception("400");
            }
            throw new Exception("500");
        }
    }
    public CheckCouponResponse checkCoupon(String couponCode, float price) {
        Coupon coupon = couponRepo.findFirstByCode(couponCode);
        if(coupon==null) return new CheckCouponResponse("Can not use",coupon);
        if(!coupon.getIsUsable()) return new CheckCouponResponse("Can not use",coupon);
        if(coupon.getMinOrderValue()>price) return new CheckCouponResponse("Need buy "+(int)(coupon.getMinOrderValue()-price)+" đ more to use",coupon);
        
        return new CheckCouponResponse("You can use this coupon",coupon);
    }
}
