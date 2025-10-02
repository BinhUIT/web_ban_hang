package com.example.webbanghang.model.response;

import java.util.HashMap;
import java.util.Map;

public class CompositeStatisticModel {
    private Map<String, StatisticModel> statisticByProduct;
    private Map<String, StatisticModel> statisticByCategory;
    public CompositeStatisticModel() {
        this.statisticByProduct= new HashMap<>();
        this.statisticByCategory= new HashMap<>();
    } 
    public CompositeStatisticModel(Map<String, StatisticModel> statisticByProduct, Map<String, StatisticModel> statisticByCategory) {
        this.statisticByCategory= statisticByCategory;
        this.statisticByProduct= statisticByProduct;
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
