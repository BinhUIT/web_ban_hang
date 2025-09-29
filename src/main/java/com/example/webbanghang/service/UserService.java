package com.example.webbanghang.service;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.webbanghang.middleware.Constants;
import com.example.webbanghang.model.entity.Cart;
import com.example.webbanghang.model.entity.CartItem;
import com.example.webbanghang.model.entity.Coupon;
import com.example.webbanghang.model.entity.Order;
import com.example.webbanghang.model.entity.OrderItem;
import com.example.webbanghang.model.entity.Payment;
import com.example.webbanghang.model.entity.Product;
import com.example.webbanghang.model.entity.ProductVariant;
import com.example.webbanghang.model.entity.User;
import com.example.webbanghang.model.enums.EDiscountType;
import com.example.webbanghang.model.enums.EOrderStatus;
import com.example.webbanghang.model.enums.EPaymentType;
import com.example.webbanghang.model.redismodel.CartData;
import com.example.webbanghang.model.request.AddToCartRequest;
import com.example.webbanghang.model.request.LoginRequest;
import com.example.webbanghang.model.request.OrderSubInfo;
import com.example.webbanghang.model.request.UpdateCartRequest;
import com.example.webbanghang.model.response.CheckCouponResponse;
import com.example.webbanghang.model.response.CreateOrderResponse;
import com.example.webbanghang.model.response.LoginResponse;
import com.example.webbanghang.repository.CouponRepository;
import com.example.webbanghang.repository.OrderItemRepository;
import com.example.webbanghang.repository.OrderRepository;
import com.example.webbanghang.repository.PaymentRepository;
import com.example.webbanghang.repository.ProductRepository;
import com.example.webbanghang.repository.ProductVariantRepository;
import com.example.webbanghang.repository.UserRepository;
@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepo;
    
    
    
   
    public UserService(UserRepository userRepo){
        this.userRepo= userRepo;
    }
        
        
        
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(username);
        if(user==null) {
            throw new UsernameNotFoundException("User not found");
        } 
        return user;
    }
    
   
        
        
    
    
    
    
    
    
    
    
}
