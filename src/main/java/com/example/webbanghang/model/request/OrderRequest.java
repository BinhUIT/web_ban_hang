package com.example.webbanghang.model.request;

import com.example.webbanghang.model.redismodel.CartData;

public class OrderRequest {
    private CartData cartData;
    private OrderSubInfo subInfo;

    public OrderRequest(CartData cartData, OrderSubInfo subInfo) {
        this.cartData = cartData;
        this.subInfo = subInfo;
    }

    public CartData getCartData() {
        return cartData;
    }

    public void setCartData(CartData cartData) {
        this.cartData = cartData;
    }

    public OrderSubInfo getSubInfo() {
        return subInfo;
    }

    public void setSubInfo(OrderSubInfo subInfo) {
        this.subInfo = subInfo;
    }

    public OrderRequest() {
    }

}
