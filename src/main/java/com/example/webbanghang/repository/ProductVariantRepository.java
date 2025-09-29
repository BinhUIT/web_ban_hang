package com.example.webbanghang.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.webbanghang.model.entity.Product;
import com.example.webbanghang.model.entity.ProductVariant;

@Repository
public interface ProductVariantRepository extends JpaRepository<ProductVariant, Integer>{
    public List<ProductVariant> findByProductColor_IdIn(List<Integer> colorIds);
    public List<ProductVariant> findByProductSize_IdIn(List<Integer> sizeIds);
    public boolean existsByProductAndProductColor_IdAndProductSize_Id(Product product, int colorId, int sizeId);
}
