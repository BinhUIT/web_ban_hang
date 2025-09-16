package com.example.webbanghang.model.entity;

import java.util.Date;
import java.util.List;

import com.example.webbanghang.model.enums.EOrderStatus;
import com.example.webbanghang.model.enums.EPaymentType;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
    private String email;
    private String address;
    private String phone;
    @OneToMany(mappedBy = "order") 
    private List<OrderItem> orderItems;
    @OneToOne(mappedBy="order") 
    private Payment payment;
    
    public Order(int id, User user, Date createAt, Date updateAt, EOrderStatus status, float shipping_fee, float total,
            List<OrderItem> orderItems,String email, String address, String phone, Payment payment) {
        this.id = id;
        this.user = user;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.status = status;
        this.shipping_fee = shipping_fee;
        this.total = total;
        this.orderItems = orderItems;
        this.email= email;
        this.address = address;
        this.phone= phone;
        this.payment = payment;
    }
    public Order(User user, String email, String address, String phone) {
        this.user = user;
        this.createAt = new Date();
        this.email= (email==null)?user.getEmail():email;
        this.address = (address==null)?user.getAddress():address;
        this.phone = (phone==null)?user.getPhone():phone;
        this.status= EOrderStatus.PENDING;
    }
    public boolean getIsPaid() {
        if(this.payment==null) return false;
        return this.payment.getStatus().equals("Success");
    }
    @JsonIgnore
    public Payment getPayment() {
        return this.payment;
    }
    public EPaymentType getPaymentType() {
        if(this.payment==null) {
            return null;
        }
        return this.payment.getPaymentType();
    }
    public void setPayment(Payment payment) {
        this.payment = payment;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    

}
