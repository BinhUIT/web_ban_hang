package com.example.webbanghang.service.adminservice;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.webbanghang.model.entity.Order;
import com.example.webbanghang.model.entity.OrderItem;
import com.example.webbanghang.model.enums.EOrderStatus;
import com.example.webbanghang.model.response.CompositeStatisticModel;
import com.example.webbanghang.model.response.StatisticModel;
import com.example.webbanghang.repository.OrderRepository;
import com.example.webbanghang.service.orderservice.OrderService;

@Service
public class AdminStatisticService {

    
    private final OrderService orderService;
    private final OrderRepository orderRepo;
    public AdminStatisticService(OrderService orderService, OrderRepository orderRepo) {
        this.orderService= orderService;
        this.orderRepo= orderRepo;
    }
    public float inComeStatistic(Date from, Date to) {
        List<Order> listOrder = orderRepo.findByPayAtBetweenAndStatus(from, to, EOrderStatus.RECEIVED);
        float res=0;
        for(Order i:listOrder) {
            res+=i.getTotal();
        }
        return res;
    } 
    public CompositeStatisticModel getStatisticBy(Date from, Date to) {
        List<Order> listOrder = orderRepo.findByPayAtBetweenAndStatus(from, to, EOrderStatus.RECEIVED);
        CompositeStatisticModel res= new CompositeStatisticModel();
        for(Order i: listOrder) {
            for(OrderItem item: i.getOrderItems()) {
                String catName = item.getProductVariant().getProduct().getCategory().getName();
                String productName = item.getProductVariant().getProduct().getName();
                res.setTotalIncome(res.getTotalIncome()+item.getTotalPrice());
                putDataToMap(res.getStatisticByCategory(), catName, item);
                putDataToMap(res.getStatisticByProduct(), productName, item);
            }
            res.setTotalIncome(res.getTotalIncome()-i.getDiscount());
            res.setTotalOrders(res.getTotalOrders()+1);
        }
        return res;
    }
    private void putDataToMap(Map<String, StatisticModel> map, String key, OrderItem item) {
        if(!map.containsKey(key)) {
                    StatisticModel newModel= new StatisticModel(item.getAmount(), item.getTotalPrice());
                    map.put(key, newModel);
                }
                else {
                    StatisticModel model=map.get(key);
                    model.setAmount(model.getAmount()+item.getAmount());
                    model.setTotalPrice(model.getTotalPrice()+item.getTotalPrice());
                }
    }
    

}
