package com.example.webbanghang.service.productservice;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.webbanghang.model.entity.Product;
import com.example.webbanghang.repository.ProductRepository;

@Service
public class ProductService {
    private final ProductRepository productRepo;
    public ProductService(ProductRepository productRepo) {
        this.productRepo = productRepo;
    }
    

    public Product getById(int id) {
        return productRepo.findById(id).orElse(null);
    }

    public List<Product> findAllProducts() {
        return productRepo.findAll();
    }

    public Page<Product> findAllProducts(Pageable pageable) {
        return productRepo.findAll(pageable);
    }

    public Page<Product> findByListIds(List<Integer> listIds, Pageable pageable) {
        return productRepo.findByIdIn(listIds, pageable);
    }

    public Page<Product> findAllEnableProductsByPage(Pageable pageable) {
        return productRepo.findByIsEnable(true, pageable);
    }


    

    
}
