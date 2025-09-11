package com.example.webbanghang.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.webbanghang.model.entity.Product;
import com.example.webbanghang.model.esmodels.Products;
import com.example.webbanghang.service.ESService;

@RestController
public class ESController {
    private ESService esService;
    public ESController(ESService esService) {
        this.esService= esService;
    } 
    @PostMapping("/unsecure/save_es_product") 
    public boolean saveESProducts(@RequestBody List<Product> products) {
        return esService.saveAllCurrentProduct(products);
    }
    @GetMapping("/unsecure/find_es/{id}") 
    public Products findESById(@PathVariable int id) {
        return esService.findById(id);
    }
}
