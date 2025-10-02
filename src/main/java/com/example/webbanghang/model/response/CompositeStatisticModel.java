package com.example.webbanghang.model.response;

import java.util.HashMap;
import java.util.Map;

public class CompositeStatisticModel {
    private Map<String, StatisticModel> statisticByProduct;
    private Map<String, StatisticModel> statisticByCategory;
    private float totalIncome;
    private int totalOrders;

    public CompositeStatisticModel() {
        this.statisticByProduct= new HashMap<>();
        this.statisticByCategory= new HashMap<>();
        this.totalIncome=0;
        this.totalOrders=0;
    } 
    public CompositeStatisticModel(Map<String, StatisticModel> statisticByProduct, Map<String, StatisticModel> statisticByCategory, float totalIncome, int totalOrders) {
        this.statisticByCategory= statisticByCategory;
        this.statisticByProduct= statisticByProduct;
        this.totalIncome = totalIncome;
        this.totalOrders= totalOrders;
    }
    public float getTotalIncome() {
        return this.totalIncome;
    } 
    public int getTotalOrders() {
        return this.totalOrders;
    } 
    public void setTotalIncome(float totalIncome) {
        this.totalIncome= totalIncome;
    } 
    public void setTotalOrders(int totalOrders) {
        this.totalOrders= totalOrders;
    }
    public Map<String, StatisticModel> getStatisticByProduct() {
        return statisticByProduct;
    }

    public void setStatisticByProduct(Map<String, StatisticModel> statisticByProduct) {
        this.statisticByProduct = statisticByProduct;
    }

    public Map<String, StatisticModel> getStatisticByCategory() {
        return statisticByCategory;
    }

    public void setStatisticByCategory(Map<String, StatisticModel> statisticByCategory) {
        this.statisticByCategory = statisticByCategory;
    }

}
