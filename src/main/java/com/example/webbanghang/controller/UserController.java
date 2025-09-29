package com.example.webbanghang.controller;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.webbanghang.middleware.ExceptionHandler;
import com.example.webbanghang.model.entity.Cart;
import com.example.webbanghang.model.entity.CartItem;
import com.example.webbanghang.model.entity.Order;
import com.example.webbanghang.model.redismodel.CartData;
import com.example.webbanghang.model.request.AddToCartRequest;
import com.example.webbanghang.model.request.LinkAccountRequest;
import com.example.webbanghang.model.request.LoginRequest;
import com.example.webbanghang.model.request.OrderRequest;
import com.example.webbanghang.model.request.UpdateCartRequest;
import com.example.webbanghang.model.response.CheckoutResponse;
import com.example.webbanghang.model.response.CreateOrderResponse;
import com.example.webbanghang.model.response.LoginResponse;
import com.example.webbanghang.model.response.Response;

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
    
    
    
    
    
    @PostMapping("/user/order") 
    public ResponseEntity<Response> order(Authentication auth, @RequestBody OrderRequest request) {
        String email = auth.getName();
        try {
            Cart cart = cartService.createCartFromMetaData(request.getCartData(), email);
            
            CreateOrderResponse response = orderService.orderFromCart(email, request.getSubInfo(), cart);
            return new ResponseEntity<>(new Response("Success",response,200), HttpStatus.OK);
        }
        catch (Exception e) {
             ResponseStatusException ex=ExceptionHandler.getResponseStatusException(e);
            return new ResponseEntity<>(new Response(ex.getMessage(),null,ex.getStatusCode().value()), HttpStatusCode.valueOf(ex.getStatusCode().value()));
        }
        
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
    @GetMapping("/user/order-history") 
    public Page<Order> getOrderHistory(Authentication auth, @RequestParam("page") int page, @RequestParam("size") int size) {
        Pageable pageable = PageRequest.of(page,size);
        String email= auth.getName();
        return orderService.getUserOrderHistory(email, pageable);
    }
    @GetMapping("/user/order-by-id/{id}") 
    public Order getOrderById(Authentication auth, @PathVariable int id) {
        String email = auth.getName();
        try {
            return orderService.getOrderById(email, id);
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
    @PutMapping("/user/cancel-order/{id}") 
    public Order cancelOrder(Authentication auth, @PathVariable int id) {
        String email = auth.getName();
        try {
            return orderService.cancelOrder(email, id);
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
    @PutMapping("user/apply_coupon")
    public String putMethodName(@RequestParam int orderId, @RequestParam String couponCode, Authentication auth) {
       String email = auth.getName();
       try {
         Order order = orderService.applyCoupon(orderId, couponCode, email);
         return "Success";
       }
        catch(Exception e) {
            if(e.getMessage().equals("404")){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
            if(e.getMessage().equals("400")) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
            if(e.getMessage().equals("Need buy more")) {
                return "You need to buy more to use this coupon code";
            }
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/user/received_order/{id}") 
    public Order receivedOrder(Authentication auth, @PathVariable int id) {
        String email = auth.getName();
        try {
            return orderService.receivedOrder(id, email);
        } catch (Exception e) {
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
