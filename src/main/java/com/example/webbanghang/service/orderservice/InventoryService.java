package com.example.webbanghang.service.orderservice;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.webbanghang.model.entity.OrderItem;
import com.example.webbanghang.model.entity.Product;
import com.example.webbanghang.model.entity.ProductVariant;
import com.example.webbanghang.repository.ProductRepository;
import com.example.webbanghang.repository.ProductVariantRepository;

@Service
public class InventoryService {
    private final ProductRepository productRepo;
    private final ProductVariantRepository productVariantRepo;
    public InventoryService(ProductRepository productRepo, ProductVariantRepository productVariantRepo) {
        this.productRepo = productRepo;
        this.productVariantRepo= productVariantRepo;
    } 
    public void restoreInventory(List<OrderItem> items) {
        List<Product> products = new ArrayList<>();
        List<ProductVariant> variants = new ArrayList<>();
        for (OrderItem item : items) {
            ProductVariant pv = item.getProductVariant();
            pv.setQuantity(pv.getQuantity() + item.getAmount());

            Product p = pv.getProduct();
            p.setQuantity(p.getQuantity() + item.getAmount());

            products.add(p);
            variants.add(pv);
        }
        productRepo.saveAll(products);
        productVariantRepo.saveAll(variants);
    }
    public void decreaseInventory(List<OrderItem> items) {
        List<Product> products = new ArrayList<>();
        List<ProductVariant> variants = new ArrayList<>();
        for (OrderItem item : items) {
            ProductVariant pv = item.getProductVariant();
            pv.setQuantity(pv.getQuantity() - item.getAmount());

            Product p = pv.getProduct();
            p.setQuantity(p.getQuantity() - item.getAmount());
            p.setSold(p.getSold() + item.getAmount());

            products.add(p);
            variants.add(pv);
        }
        productRepo.saveAll(products);
        productVariantRepo.saveAll(variants);
    }


}
