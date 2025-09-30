package com.example.webbanghang.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.webbanghang.model.entity.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {
    public RefreshToken findByToken(String token);
}
