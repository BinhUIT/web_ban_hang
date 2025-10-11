package com.example.webbanghang.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.webbanghang.model.entity.Product;
@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    
    
    public Page<Product> findByIsEnable(boolean isEnable, Pageable pageable);
    public Page<Product> findByIdIn(List<Integer> listId, Pageable pageable); 
    public Page<Product> findByIsEnableAndIdIn(boolean isEnable, List<Integer> listId, Pageable pageable);
    public Page<Product> findByIsEnableAndMinPriceBetween(boolean isEnable, Integer from, Integer to, Pageable pageable); 
    public Page<Product> findByIsEnableAndCategory_IdIn(boolean isEnable, List<Long> listCatIds, Pageable pageable);
    public Page<Product> findAll(Pageable pageable);
}
