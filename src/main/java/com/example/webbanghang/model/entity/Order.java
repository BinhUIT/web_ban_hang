package com.example.webbanghang.model.entity;

import java.util.Date;
import java.util.List;

import com.example.webbanghang.model.enums.EOrderStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="ORDERS")
public class Order { 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private int id; 
    @ManyToOne 
    @JoinColumn(name = "user_id") 
    private User user;
    private Date createAt;
    private Date updateAt;
    private EOrderStatus status;
    private float shipping_fee;
    private float total;
    @OneToMany(mappedBy = "order") 
    private List<OrderItem> orderItems;
    public Order(int id, User user, Date createAt, Date updateAt, EOrderStatus status, float shipping_fee, float total,
            List<OrderItem> orderItems) {
        this.id = id;
        this.user = user;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.status = status;
        this.shipping_fee = shipping_fee;
        this.total = total;
        this.orderItems = orderItems;
    }
    public List<OrderItem> getOrderItems() {
        return orderItems;
    }
    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public Date getCreateAt() {
        return createAt;
    }
    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
    public Date getUpdateAt() {
        return updateAt;
    }
    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }
    public EOrderStatus getStatus() {
        return status;
    }
    public void setStatus(EOrderStatus status) {
        this.status = status;
    }
    public float getShipping_fee() {
        return shipping_fee;
    }
    public void setShipping_fee(float shipping_fee) {
        this.shipping_fee = shipping_fee;
    }
    public float getTotal() {
        return total;
    }
    public void setTotal(float total) {
        this.total = total;
    }
    public Order() {
    } 
    

}
