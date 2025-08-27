package com.example.webbanghang.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.webbanghang.model.entity.WishList;

@Repository
public interface WishListRepostitory extends JpaRepository<WishList, Integer>{
    public List<WishList> findByUser_Id(long userId);
}
