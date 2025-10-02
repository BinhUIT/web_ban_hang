package com.example.webbanghang.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.webbanghang.model.entity.Order;
import com.example.webbanghang.model.enums.EOrderStatus;
@Repository
public interface OrderRepository extends JpaRepository<Order,Integer> {

    public List<Order> findByUser_Id(long userId);
    public Page<Order> findByUser_IdOrderByCreateAtDesc(long userId, Pageable pageable);
    public Order findFirstByPaymentCode(Long paymentCode);
    public List<Order> findByPayAtBetweenAndStatus(Date start, Date end, EOrderStatus status);
    

}
