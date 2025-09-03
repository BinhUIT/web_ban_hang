package com.example.webbanghang.service;

import org.springframework.stereotype.Service;

import com.example.webbanghang.model.entity.Product;
import com.example.webbanghang.repository.ProductRepository;

@Service
public class ProductService {
    private ProductRepository productRepo;
    public ProductService(ProductRepository productRepo) {
        this.productRepo= productRepo;
    } 
    public Product getById(int id) {
        return productRepo.findById(id).orElse(null);
    }
}
