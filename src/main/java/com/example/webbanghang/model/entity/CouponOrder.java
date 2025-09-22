package com.example.webbanghang.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="Coupon_Order")
public class CouponOrder {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY) 
    private int id;
    @ManyToOne
    @JoinColumn(name="coupon_id")
    private Coupon coupon;
    
}
