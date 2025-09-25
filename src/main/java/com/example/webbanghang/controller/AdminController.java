package com.example.webbanghang.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.example.webbanghang.middleware.ExceptionHandler;
import com.example.webbanghang.model.entity.Coupon;
import com.example.webbanghang.model.entity.Order;
import com.example.webbanghang.model.entity.Product;
import com.example.webbanghang.model.entity.ProductVariant;
import com.example.webbanghang.model.entity.User;
import com.example.webbanghang.model.request.CreateCouponRequest;
import com.example.webbanghang.model.request.CreateProductRequest;
import com.example.webbanghang.model.request.CreateProductVariantRequest;
import com.example.webbanghang.service.AdminService;
import com.example.webbanghang.service.CouponService;

@RestController
public class AdminController {
    private final AdminService adminService;
    private final CouponService couponService;
    public AdminController(AdminService adminService, CouponService couponService) {
        this.adminService= adminService;
        this.couponService = couponService;
    } 
    @GetMapping("/admin/get_orders") 
    public Page<Order> getOrders(@RequestParam int size, @RequestParam int page) {
        return this.adminService.findAllOrder(size, page);
    }
    @GetMapping("/admin/get_order/{id}") 
    public Order getOrderById(@PathVariable int id) {
        return this.adminService.getOrderById(id);
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
    @PostMapping("/admin/create_coupon") 
    public Coupon createCoupon(@RequestBody CreateCouponRequest request) {
        try {
            return couponService.createCoupon(request);
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
    @GetMapping("/admin/all_user") 
    public Page<User> getAllUser(@RequestParam int size, @RequestParam int number) {
        return adminService.findAllUser(size, number);
    }
    @PostMapping("/admin/create_product") 
    public Product createProduct(@RequestPart("image") MultipartFile image, @RequestPart("info") CreateProductRequest request) {
        try {
            return adminService.createProduct(request, image);
        } 
        catch(Exception e) {
            throw ExceptionHandler.getResponseStatusException(e);
        }
    }
    @PostMapping("admin/add_variant/{productId}")
    public ProductVariant addVariant(@RequestPart("image") MultipartFile image, @RequestPart("info") CreateProductVariantRequest request, @PathVariable int productId) {
        try {
            return adminService.addVariant(request, productId, image);
        }
        catch(Exception e) {
            throw ExceptionHandler.getResponseStatusException(e);
        }
    }
    
}
