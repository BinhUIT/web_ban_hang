package com.example.webbanghang.service.productservice;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.webbanghang.model.entity.Product;
import com.example.webbanghang.model.entity.ProductVariant;
import com.example.webbanghang.repository.ProductVariantRepository;

@Service
public class ProductVariantService {
    private final ProductVariantRepository productVariantRepo;

    public ProductVariantService(ProductVariantRepository productVariantRepo) {
        this.productVariantRepo = productVariantRepo;
    }

    public boolean isVariantExist(int colorId, int sizeId, Product product) {
        return productVariantRepo.existsByProductAndProductColor_IdAndProductSize_Id(product, colorId, sizeId);
    }

    public ProductVariant getVariantById(int variantId) {
        return productVariantRepo.findById(variantId).orElse(null);
    }

    public Set<Integer> extractProductIdFromVariants(List<ProductVariant> variants) {
        return variants.stream()
                .map(v -> v.getProduct().getId())
                .collect(Collectors.toSet());
    }
}
