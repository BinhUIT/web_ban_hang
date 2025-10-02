package com.example.webbanghang.model.response;

public class StatisticModel {
    private int amount;
    private float totalPrice;
    public StatisticModel() {

    } 
    public StatisticModel(int amount, float totalPrice) {
        this.amount=amount;
        this.totalPrice= totalPrice;
    } 
    public int getAmount() {
        return this.amount;
    } 
    public float getTotalPrice() {
        return this.totalPrice;
    } 
    public void setAmount(int amount) {
        this.amount = amount;
    } 
    public void setTotalPrice(float totalPrice) {
        this.totalPrice= totalPrice;
    }
}
