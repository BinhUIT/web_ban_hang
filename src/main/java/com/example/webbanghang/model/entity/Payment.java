package com.example.webbanghang.model.entity;

import java.util.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "PAYMENTS")
public class Payment  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private int id; 
    @OneToOne(cascade = CascadeType.ALL) 
    @JoinColumn(name="order_id") 
    private Order order;
    private String currency;
    private float total;
    private Date createAt;
    private Date updateAt;
    private String status;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Order getOrder() {
        return order;
    }
    public void setOrder(Order order) {
        this.order = order;
    }
    public String getCurrency() {
        return currency;
    }
    public void setCurrency(String currency) {
        this.currency = currency;
    }
    public float getTotal() {
        return total;
    }
    public void setTotal(float total) {
        this.total = total;
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
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public Payment() {
    }
    public Payment(int id, Order order, String currency, float total, Date createAt, Date updateAt, String status) {
        this.id = id;
        this.order = order;
        this.currency = currency;
        this.total = total;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.status = status;
    }
    public Payment(Order order, String currency, String status) {
        this.order= order;
        this.currency = currency;
        this.total= order.getTotal();
        this.createAt= new Date();
        this.updateAt=null;
        this.status=status;
    }

}
