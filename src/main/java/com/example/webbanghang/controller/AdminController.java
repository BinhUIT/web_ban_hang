package com.example.webbanghang.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
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
import com.example.webbanghang.model.response.CompositeStatisticModel;
import com.example.webbanghang.model.response.GetSizeAndColorResponse;
import com.example.webbanghang.model.response.Response;
import com.example.webbanghang.service.ColorService;
import com.example.webbanghang.service.SizeService;
import com.example.webbanghang.service.adminservice.AdminOrderService;
import com.example.webbanghang.service.adminservice.AdminProductService;
import com.example.webbanghang.service.adminservice.AdminProductVariantService;
import com.example.webbanghang.service.adminservice.AdminStatisticService;
import com.example.webbanghang.service.adminservice.AdminUserService;
import com.example.webbanghang.service.couponservice.CouponService;
import com.example.webbanghang.service.productservice.ProductService;

@RestController
public class AdminController {
    
    private final AdminOrderService adminOrderService;
    private final AdminProductService adminProductService;
    private final AdminProductVariantService adminProductVariantService;
    private final AdminUserService adminUserService;
    private final CouponService couponService;
    private final ProductService productService;
    private final ColorService colorService;
    private final SizeService sizeService;
    private final AdminStatisticService adminStatisticService;
    public AdminController(AdminOrderService adminOrderService, AdminProductService adminProductService, AdminProductVariantService adminProductVariantService, AdminUserService adminUserService, CouponService couponService,
    ProductService productService, ColorService colorService, SizeService sizeService, AdminStatisticService adminStatisticService){
        this.adminOrderService = adminOrderService;
        this.adminProductService = adminProductService;
        this.adminProductVariantService= adminProductVariantService;
        this.couponService= couponService;
        this.adminUserService = adminUserService;
        this.productService= productService;
        this.colorService= colorService;
        this.sizeService= sizeService;
        this.adminStatisticService = adminStatisticService;
    }
    @GetMapping("/admin/get_orders") 
    public Page<Order> getOrders(@RequestParam int size, @RequestParam int page) {
        return this.adminOrderService.findAllOrder(size, page);
    }
    @GetMapping("/admin/get_order/{id}") 
    public Order getOrderById(@PathVariable int id) {
        return this.adminOrderService.getOrderById(id);
    }
    @PutMapping("/admin/cancel_order/{orderId}") 
    public String cancelOrder(@PathVariable int orderId) {
        
        adminOrderService.cancelOrder(orderId);
        return "Success";
        
    }
    
    @PutMapping("/admin/ship_order/{orderId}") 
    public String shipOrder(@PathVariable int orderId) {
        
        adminOrderService.shipOrder(orderId);
        return "Success";
    }    
    @PostMapping("/admin/create_coupon") 
    public ResponseEntity<Response> createCoupon(@RequestBody CreateCouponRequest request) {
        try {
            Coupon coupon= couponService.createCoupon(request);
            Response response = new Response("Success", coupon, 200);
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (Exception e) {
           ResponseStatusException ex=ExceptionHandler.getResponseStatusException(e);
            return new ResponseEntity<>(new Response(ex.getMessage(),null,ex.getStatusCode().value()), HttpStatusCode.valueOf(ex.getStatusCode().value()));
        }
    }
    @GetMapping("/admin/all_user") 
    public Page<User> getAllUser(@RequestParam int size, @RequestParam int number) {
        return adminUserService.findAllUser(size, number);
    }
    @PostMapping("/admin/create_product") 
    public ResponseEntity<Response> createProduct(@RequestPart("image") MultipartFile image, @RequestPart("info") CreateProductRequest request) {
        try {
            return new ResponseEntity(new Response("Success",adminProductService.createProduct(request, image),200),HttpStatus.OK);
        } 
        catch(Exception e) {
            ResponseStatusException ex=ExceptionHandler.getResponseStatusException(e);
            return new ResponseEntity<>(new Response(ex.getMessage(),null,ex.getStatusCode().value()), HttpStatusCode.valueOf(ex.getStatusCode().value()));
        }
    }
    @PostMapping("/admin/add_variant/{productId}")
    public ResponseEntity<Response> addVariant(@RequestPart("image") MultipartFile image, @RequestPart("info") CreateProductVariantRequest request, @PathVariable int productId) {
        try {
            ProductVariant v=adminProductVariantService.addVariant(request, productId, image);
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
            Product p = adminProductService.updateProduct(request, productId, image);
            return new ResponseEntity<>(new Response("Success",p,200), HttpStatus.OK);
        }
        catch(Exception e) {
            ResponseStatusException ex=ExceptionHandler.getResponseStatusException(e);
            return new ResponseEntity<>(new Response(ex.getMessage(),null,ex.getStatusCode().value()), HttpStatusCode.valueOf(ex.getStatusCode().value()));
        }
    }
    @PutMapping("/admin/update_product_variant/{variantId}")
    public ResponseEntity<Response> updateProductVariant(@RequestPart(value="image", required=false) MultipartFile file, @RequestPart(value="info", required=false) UpdateProductVariantRequest request, @PathVariable int variantId) {
        try {
            ProductVariant pv =adminProductVariantService.updateProductVariant(request, variantId, file);
            Response response = new Response("Success",pv,200);
            return new ResponseEntity(response, HttpStatus.OK);
        } 
        catch(Exception e) {
           ResponseStatusException ex=ExceptionHandler.getResponseStatusException(e);
            return new ResponseEntity<>(new Response(ex.getMessage(),null,ex.getStatusCode().value()), HttpStatusCode.valueOf(ex.getStatusCode().value()));
        }
    }
    @DeleteMapping("/admin/delete_product/{productId}") 
    public ResponseEntity<Map<String,String>> deleteProduct(@PathVariable int productId) {
    
            adminProductService.deleteProduct(productId);
            Map<String,String> res = Map.of("message","success");
            return new ResponseEntity<>(res, HttpStatus.OK);
       
    }
    @DeleteMapping("/admin/delete_product_variant/{productVariantId}") 
    public ResponseEntity<Map<String,String>> deleteProductVariant(@PathVariable int productVariantId) {
        
            adminProductVariantService.deleteProductVariant(productVariantId); 
            Map<String,String> res = new HashMap<>();
            res.put("message", "Success");
            return new ResponseEntity<>(res, HttpStatus.OK);
        
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
    @GetMapping("/admin/get_statistic") 
    public ResponseEntity<Response> getStatisticData(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") Date from, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") Date to) {
        CompositeStatisticModel result = adminStatisticService.getStatisticBy(from, to);
        Response response = new Response("Success",result,200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
