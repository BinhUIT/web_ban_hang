package com.example.webbanghang.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.webbanghang.model.entity.ProductAttribute;
@Repository
public interface ProductAttributeRepository extends JpaRepository<ProductAttribute, Long> {

}
