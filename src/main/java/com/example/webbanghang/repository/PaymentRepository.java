package com.example.webbanghang.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.webbanghang.model.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {

}
