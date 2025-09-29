package com.example.webbanghang.controller.cartcontroller;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.webbanghang.middleware.ExceptionHandler;
import com.example.webbanghang.model.entity.Cart;
import com.example.webbanghang.model.redismodel.CartData;
import com.example.webbanghang.model.response.Response;
import com.example.webbanghang.service.cartservice.CartService;
@RestController

public class CartController {
    private final CartService cartService;
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }
    @PostMapping("/user/get_cart") 
    public ResponseEntity<Response> getCart(@RequestBody CartData cartData) {
        try {
            Cart cart = cartService.getCartFromMetaData(cartData);
            return new ResponseEntity<>(new Response("Success",cart,200), HttpStatus.OK);
        }
        catch(Exception e) {
            ResponseStatusException ex=ExceptionHandler.getResponseStatusException(e);
            return new ResponseEntity<>(new Response(ex.getMessage(),null,ex.getStatusCode().value()), HttpStatusCode.valueOf(ex.getStatusCode().value()));
        }
    }
    
}
