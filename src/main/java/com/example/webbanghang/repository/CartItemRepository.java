package com.example.webbanghang.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.webbanghang.model.entity.CartItem;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    public List<CartItem> findByProductVariant_IdIn(List<Integer> listId);
    public List<CartItem> findByProductVariant_Id(int variantId);
}
