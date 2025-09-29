package com.example.webbanghang.controller.ordercontroller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.webbanghang.middleware.ExceptionHandler;
import com.example.webbanghang.model.entity.Cart;
import com.example.webbanghang.model.entity.Order;
import com.example.webbanghang.model.request.OrderRequest;
import com.example.webbanghang.model.response.CreateOrderResponse;
import com.example.webbanghang.model.response.Response;
import com.example.webbanghang.service.cartservice.CartService;
import com.example.webbanghang.service.orderservice.OrderService;

@RestController
@RequestMapping("/user")
public class OrderController {
    private final OrderService orderService;
    private final CartService cartService;
    public OrderController(OrderService orderService, CartService cartService) {
        this.orderService = orderService;
        this.cartService = cartService;
    } 
    @PostMapping("/order") 
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
    @GetMapping("/order-history") 
    public Page<Order> getOrderHistory(Authentication auth, @RequestParam("page") int page, @RequestParam("size") int size) {
        Pageable pageable = PageRequest.of(page,size);
        String email= auth.getName();
        return orderService.getUserOrderHistory(email, pageable);
    }
    @GetMapping("/order-by-id/{id}") 
    public ResponseEntity<Response> getOrderById(Authentication auth, @PathVariable int id) {
        String email = auth.getName();
        try {
            Order order = orderService.getOrderById(email, id);
            Response response = new Response("Success", order,200);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        catch(Exception e) {
            ResponseStatusException ex=ExceptionHandler.getResponseStatusException(e);
            return new ResponseEntity<>(new Response(ex.getMessage(),null,ex.getStatusCode().value()), HttpStatusCode.valueOf(ex.getStatusCode().value()));
        }
    }
    @PutMapping("/cancel-order/{id}") 
    public ResponseEntity<Response> cancelOrder(Authentication auth, @PathVariable int id) {
        String email = auth.getName();
        try {
            Order order= orderService.cancelOrder(email, id);
            Response response = new Response("Success", order,200);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        catch(Exception e) {
            ResponseStatusException ex=ExceptionHandler.getResponseStatusException(e);
            return new ResponseEntity<>(new Response(ex.getMessage(),null,ex.getStatusCode().value()), HttpStatusCode.valueOf(ex.getStatusCode().value()));
        }
    }
    @PutMapping("/received_order/{id}") 
    public ResponseEntity<Response> receivedOrder(Authentication auth, @PathVariable int id) {
        String email = auth.getName();
        try {
            Order order =orderService.receivedOrder(id, email);
             Response response = new Response("Success", order,200);
            return new ResponseEntity<>(response,HttpStatus.OK);
        } catch (Exception e) {
            ResponseStatusException ex=ExceptionHandler.getResponseStatusException(e);
            return new ResponseEntity<>(new Response(ex.getMessage(),null,ex.getStatusCode().value()), HttpStatusCode.valueOf(ex.getStatusCode().value()));
        }
    }

}
