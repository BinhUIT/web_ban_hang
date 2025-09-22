package com.example.webbanghang.service;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.webbanghang.model.entity.Order;
import com.example.webbanghang.model.entity.Payment;
import com.example.webbanghang.model.enums.EOrderStatus;
import com.example.webbanghang.repository.OrderRepository;
import com.example.webbanghang.repository.PaymentRepository;

@Service
public class AdminService {
    private final OrderRepository orderRepo;
    private final PaymentRepository paymentRepo;
    public AdminService(OrderRepository orderRepo, PaymentRepository paymentRepo) {
        this.orderRepo = orderRepo;
        this.paymentRepo= paymentRepo;
    } 
    public Page<Order> findAllOrder(int size, int number) {
        Pageable pageable = PageRequest.of(number,size,Sort.by(Sort.Direction.DESC,"createAt"));
        return orderRepo.findAll(pageable);
    }
    public void cancelOrder(int orderId) throws Exception {
        Order order = orderRepo.findById(orderId).orElse(null);
        if(order==null) {
            throw new Exception("404");
        } 
        if(order.getStatus()!=EOrderStatus.PENDING) {
            throw new Exception("400");
        } 
        order.setStatus(EOrderStatus.CANCELED);
        order.setUpdateAt(new Date()); 
        orderRepo.save(order);
    }
    public void shippedOrder(int orderId) throws Exception {
         Order order = orderRepo.findById(orderId).orElse(null);
        if(order==null) {
            throw new Exception("404");
        } 
        if(order.getStatus()!=EOrderStatus.SHIPPING) {
            throw new Exception("400");
        }
        if(!order.getIsPaid()) {
            throw new Exception("400");
        }
        Payment payment = order.getPayment();
        payment.setStatus("SUCCESS"); 
        paymentRepo.save(payment);
        order.setStatus(EOrderStatus.RECEIVED);
        order.setUpdateAt(new Date()); 
        orderRepo.save(order);
    }
    public void shipOrder(int orderId) throws Exception {
         Order order = orderRepo.findById(orderId).orElse(null);
        if(order==null) {
            throw new Exception("404");
        } 
        if(order.getStatus()!=EOrderStatus.PENDING) {
            throw new Exception("400");
        }
        if(!order.getIsPaid()) {
            throw new Exception("400");
        }
        order.setStatus(EOrderStatus.SHIPPING);
        order.setUpdateAt(new Date()); 
        orderRepo.save(order);
    }

}
