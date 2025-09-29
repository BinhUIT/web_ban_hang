package com.example.webbanghang.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import com.example.webbanghang.model.request.UpdateProductRequest;
import com.example.webbanghang.model.request.UpdateProductVariantRequest;
import com.example.webbanghang.model.response.GetSizeAndColorResponse;
import com.example.webbanghang.model.response.Response;
import com.example.webbanghang.service.AdminService;
import com.example.webbanghang.service.ColorService;
import com.example.webbanghang.service.ProductService;
import com.example.webbanghang.service.SizeService;
import com.example.webbanghang.service.couponservice.CouponService;

@RestController
public class AdminController {
    private final AdminService adminService;
    private final CouponService couponService;
    private final ProductService productService;
    private final ColorService colorService;
    private final SizeService sizeService;
    public AdminController(AdminService adminService, CouponService couponService, ProductService productService, ColorService colorService, SizeService sizeService) {
        this.adminService= adminService;
        this.couponService = couponService;
        this.productService = productService;
        this.colorService= colorService;
        this.sizeService= sizeService;
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
    public ResponseEntity<Response> createProduct(@RequestPart("image") MultipartFile image, @RequestPart("info") CreateProductRequest request) {
        try {
            return new ResponseEntity<>(new Response("Success",adminService.createProduct(request, image),200),HttpStatus.OK);
        } 
        catch(Exception e) {
            ResponseStatusException ex=ExceptionHandler.getResponseStatusException(e);
            return new ResponseEntity<>(new Response(ex.getMessage(),null,ex.getStatusCode().value()), HttpStatusCode.valueOf(ex.getStatusCode().value()));
        }
    }
    @PostMapping("/admin/add_variant/{productId}")
    public ResponseEntity<Response> addVariant(@RequestPart("image") MultipartFile image, @RequestPart("info") CreateProductVariantRequest request, @PathVariable int productId) {
        try {
            ProductVariant v=adminService.addVariant(request, productId, image);
            return new ResponseEntity<>(new Response("Success",v,200), HttpStatus.OK);
        }
        catch(Exception e) {
             ResponseStatusException ex=ExceptionHandler.getResponseStatusException(e);
            return new ResponseEntity<>(new Response(ex.getMessage(),null,ex.getStatusCode().value()), HttpStatusCode.valueOf(ex.getStatusCode().value()));
        }
    }
    @PutMapping("/admin/update_product/{productId}") 
    public ResponseEntity<Response> updateProduct(@RequestPart(value="image", required=false) MultipartFile image, @RequestPart(value="info", required=false) UpdateProductRequest request, @PathVariable int productId) {
        try {
            Product p = adminService.updateProduct(request, productId, image);
            return new ResponseEntity<>(new Response("Success",p,200), HttpStatus.OK);
        }
        catch(Exception e) {
            ResponseStatusException ex=ExceptionHandler.getResponseStatusException(e);
            return new ResponseEntity<>(new Response(ex.getMessage(),null,ex.getStatusCode().value()), HttpStatusCode.valueOf(ex.getStatusCode().value()));
        }
    }
    @PutMapping("/admin/update_product_variant/{variantId}")
    public ProductVariant updateProductVariant(@RequestPart(value="image", required=false) MultipartFile file, @RequestPart(value="info", required=false) UpdateProductVariantRequest request, @PathVariable int variantId) {
        try {
            return adminService.updateProductVariant(request, variantId, file);
        } 
        catch(Exception e) {
            throw ExceptionHandler.getResponseStatusException(e);
        }
    }
    @DeleteMapping("/admin/delete_product/{productId}") 
    public ResponseEntity<Map<String,String>> deleteProduct(@PathVariable int productId) {
        try {
            adminService.deleteProduct(productId);
            Map<String,String> res = Map.of("message","success");
            return new ResponseEntity<>(res, HttpStatus.OK);
        } 
        catch(Exception e) {
            ResponseStatusException ex = ExceptionHandler.getResponseStatusException(e);
            return ResponseEntity
            .status(ex.getStatusCode())
            .body(Map.of("message", ex.getReason()));
        }
    }
    @DeleteMapping("/admin/delete_product_variant/{productVariantId}") 
    public ResponseEntity<Map<String,String>> deleteProductVariant(@PathVariable int productVariantId) {
        try {
            adminService.deleteProductVariant(productVariantId); 
            Map<String,String> res = new HashMap<>();
            res.put("message", "Success");
            return new ResponseEntity<>(res, HttpStatus.OK);
        } 
        catch(Exception e) {
            e.printStackTrace();
            ResponseStatusException ex = ExceptionHandler.getResponseStatusException(e);
            return ResponseEntity
            .status(ex.getStatusCode())
            .body(Map.of("message", ex.getReason()));
        }
    }
    @GetMapping("/admin/all_product") 
    public Page<Product> getAllProduct(@RequestParam int size, @RequestParam int page) {
        Pageable pageable = PageRequest.of(page,size);
        return productService.findAllProducts(pageable);
    }
    @GetMapping("/unsecure/sizes_colors") 
    public GetSizeAndColorResponse getSizeAndColor() {
        return new GetSizeAndColorResponse(sizeService.getAll(),colorService.getAllColors());
    }
}
