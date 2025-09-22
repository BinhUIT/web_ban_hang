package com.example.webbanghang.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.webbanghang.model.entity.Coupon;
import com.example.webbanghang.model.entity.Product;
import com.example.webbanghang.model.request.CreateCouponRequest;
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
    public List<Coupon> getAllUsableCouponOfProduct(int productId) {
        List<Coupon> listCouponOfProduct = couponRepo.findByProduct_Id(productId);
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
            Coupon coupon = new Coupon(request, product);
            coupon=couponRepo.save(coupon);
            return coupon;
        } catch (Exception e) {
            if(e.getMessage().equals("400")){
                throw new Exception("400");
            }
            throw new Exception("500");
        }
    }
    
}
