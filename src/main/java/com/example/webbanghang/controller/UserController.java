package com.example.webbanghang.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.webbanghang.model.entity.Order;
import com.example.webbanghang.model.response.CheckoutResponse;
import com.example.webbanghang.service.UserService;
import com.example.webbanghang.service.authservice.OAuth2Service;
import com.example.webbanghang.service.cartservice.CartService;
import com.example.webbanghang.service.orderservice.OrderService;

@RestController
public class UserController {
    private final UserService userService; 
    private final OAuth2Service oAuth2Service;
    private final CartService cartService;
    private final OrderService orderService;
    public UserController(UserService userService, OAuth2Service oAuth2Service, CartService cartService, OrderService orderService) {
        this.userService= userService;
        this.oAuth2Service= oAuth2Service;
        this.cartService = cartService;
        this.orderService= orderService;
    }
    
    
    
    
    
    
    @PostMapping("/user/check-out/{orderId}") 
    public CheckoutResponse checkOut(Authentication auth, @PathVariable int orderId) {
        String email = auth.getName();
        try {
            Order order = orderService.getOrderById(email, orderId);
            return null;
        }
        catch(Exception e) {
            if(e.getMessage().equals("404")){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
            if(e.getMessage().equals("400")) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }
    
    
    
   
    
    
    
}
