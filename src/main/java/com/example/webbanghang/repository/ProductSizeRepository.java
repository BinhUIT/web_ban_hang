package com.example.webbanghang.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.webbanghang.model.entity.ProductSize;
@Repository
public interface ProductSizeRepository extends JpaRepository<ProductSize, Integer> {

}
