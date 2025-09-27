package com.example.webbanghang.model.redismodel;

import java.util.List;

public class CartData {
    private long userId;
    private List<CartDetail> listDetails;
    public CartData() {
    }

    public CartData(List<CartDetail> listDetails, long userId) {
        this.listDetails = listDetails;
        this.userId = userId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public List<CartDetail> getListDetails() {
        return listDetails;
    }

    public void setListDetails(List<CartDetail> listDetails) {
        this.listDetails = listDetails;
    }
    
}
