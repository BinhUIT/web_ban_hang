package com.example.webbanghang.service.couponservice;

import org.springframework.stereotype.Service;

import com.example.webbanghang.exception.BadRequestException;
import com.example.webbanghang.model.entity.Coupon;
import com.example.webbanghang.model.enums.EDiscountType;
import com.example.webbanghang.model.request.CreateCouponRequest;
import com.example.webbanghang.repository.CouponRepository;

@Service
public class CouponService {
    private final CouponRepository couponRepo;
    public CouponService(CouponRepository couponRepo) {
        this.couponRepo = couponRepo;
    } 
    public Coupon validateCoupon(String code, float orderValue) {
        Coupon coupon = couponRepo.findFirstByCode(code);
        if (coupon == null) {
            throw new BadRequestException("Invalid coupon code");
        }
        if (orderValue < coupon.getMinOrderValue()) {
            throw new BadRequestException("Order does not meet minimum value for this coupon");
        }
        return coupon;
    }

    public float applyDiscount(float originPrice, Coupon coupon) {
        if (coupon.getDiscountType() == EDiscountType.FIXED) {
            return originPrice - coupon.getDiscount();
        }
        return originPrice * (1 - coupon.getDiscount());
    }

    public void increaseUsage(Coupon coupon) {
        coupon.setUsedTime(coupon.getUsedTime() + 1);
        couponRepo.save(coupon);
    }
    public Coupon createCoupon(CreateCouponRequest request) throws RuntimeException{
        Coupon coupon = new Coupon(request);
        return couponRepo.save(coupon);
    }
}
