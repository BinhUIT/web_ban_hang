package com.example.webbanghang.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.webbanghang.model.entity.Token;
@Repository
public interface TokenRepository extends JpaRepository<Token, Integer> {
    public Token findByToken(String token);
    public List<Token> findByAddAtBefore(Date date);
}
