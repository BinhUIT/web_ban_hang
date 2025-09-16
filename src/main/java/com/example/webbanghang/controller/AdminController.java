package com.example.webbanghang.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.webbanghang.model.entity.Order;
import com.example.webbanghang.service.AdminService;

@RestController
public class AdminController {
    private final AdminService adminService;
    public AdminController(AdminService adminService) {
        this.adminService= adminService;
    } 
    @GetMapping("/admin/get_orders") 
    public Page<Order> getOrders(@RequestParam("size") int size, @RequestParam("page") int page) {
        return this.adminService.findAllOrder(size, page);
    }
    @PutMapping("/admin/cancel_order/{orderId}") 
    public String cancelOrder(@PathVariable int orderId) {
        try {
            adminService.cancelOrder(orderId);
            return "Success";
        } 
        catch(Exception e) {
            if(e.getMessage().equals("404")) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            } 
            if(e.getMessage().equals("400")) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/admin/shipped_order/{orderId}")
    public String shippedOrder(@PathVariable int orderId)  {
        try {
            adminService.shippedOrder(orderId);
            return "Success";
        } catch (Exception e) {
            if(e.getMessage().equals("404")) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            } 
            if(e.getMessage().equals("400")) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/admin/ship_order/{orderId}") 
    public String shipOrder(@PathVariable int orderId) {
        try {
            adminService.shipOrder(orderId);
            return "Success";
        } 
        catch(Exception e) {
            if(e.getMessage().equals("404")) {
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
