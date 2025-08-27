package com.example.webbanghang.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.webbanghang.model.entity.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
    public List<Review> findByProduct_Id(int productId);
}
