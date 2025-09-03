package com.example.webbanghang.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.webbanghang.model.entity.Product;
import com.example.webbanghang.service.ProductService;


@RestController
public class ProductController {
    private ProductService productService;
    public ProductController(ProductService productService) {
        this.productService= productService;
    }
    @GetMapping("/unsecure/product/{id}")
    public Product getProductById(@RequestParam int id) {
        return productService.getById(id);
    }
    
}
