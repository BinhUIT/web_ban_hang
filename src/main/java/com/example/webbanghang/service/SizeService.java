package com.example.webbanghang.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.webbanghang.model.entity.ProductSize;
import com.example.webbanghang.repository.ProductSizeRepository;

@Service
public class SizeService {
    private final ProductSizeRepository productSizeRepo;
    public SizeService(ProductSizeRepository productSizeRepo) {
        this.productSizeRepo = productSizeRepo;
    } 
    public List<ProductSize> getAll() {
        return this.productSizeRepo.findAll();
    }

}
