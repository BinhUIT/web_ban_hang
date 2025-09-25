package com.example.webbanghang.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.webbanghang.model.entity.ProductColor;
import com.example.webbanghang.repository.ProductColorRepository;
@Service
public class ColorService {
    private final ProductColorRepository productColorRepo;
    public ColorService(ProductColorRepository productColorRepo) {
        this.productColorRepo= productColorRepo;
    } 
    public List<ProductColor> getAllColors() {
        return productColorRepo.findAll();
    }
}
