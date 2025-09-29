package com.example.webbanghang.service.adminservice;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.example.webbanghang.exception.BadRequestException;
import com.example.webbanghang.exception.NotFoundException;
import com.example.webbanghang.model.entity.Order;
import com.example.webbanghang.model.entity.OrderItem;
import com.example.webbanghang.model.enums.EOrderStatus;
import com.example.webbanghang.repository.OrderRepository;
import com.example.webbanghang.service.orderservice.InventoryService;


public class AdminOrderService {
    private final OrderRepository orderRepo;
    private final InventoryService inventoryService;
    public AdminOrderService(OrderRepository orderRepo, InventoryService inventoryService) {
        this.orderRepo = orderRepo;
        this.inventoryService = inventoryService;
    }

    public Page<Order> findAllOrder(int size, int number) {
        Pageable pageable = PageRequest.of(number,size,Sort.by(Sort.Direction.DESC,"createAt"));
        return orderRepo.findAll(pageable);
    }
    public void cancelOrder(int orderId) throws Exception {
        Order order = orderRepo.findById(orderId).orElse(null);
        if(order==null) {
            throw new NotFoundException("Order with id "+orderId+" not found");
        } 
        if(order.getStatus()!=EOrderStatus.PENDING) {
            throw new BadRequestException("You can not cancel this order");
        } 
        order.setStatus(EOrderStatus.CANCELED);
        order.setUpdateAt(new Date()); 
        List<OrderItem> listOrderItems= order.getOrderItems();
        inventoryService.restoreInventory(listOrderItems);
        orderRepo.save(order);
    }
    public void shipOrder(int orderId) throws Exception {
         Order order = orderRepo.findById(orderId).orElse(null);
        if(order==null) {
           throw new NotFoundException("Order with id "+orderId+" not found");
        } 
        if(order.getStatus()!=EOrderStatus.PENDING) {
            throw new BadRequestException("You can not cancel this order");
        }
        
        order.setStatus(EOrderStatus.SHIPPING);
        order.setUpdateAt(new Date()); 
        orderRepo.save(order);
        
        List<OrderItem> listOrderItems= order.getOrderItems();
        inventoryService.decreaseInventory(listOrderItems);
    }
    public Order getOrderById(int orderId) {
        return orderRepo.findById(orderId).orElse(null);
    }
}

