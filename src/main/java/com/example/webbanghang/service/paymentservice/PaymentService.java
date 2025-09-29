package com.example.webbanghang.service.paymentservice;

import java.util.ArrayList;
import java.util.List;

import com.example.webbanghang.model.entity.Order;
import com.example.webbanghang.model.entity.OrderItem;
import com.example.webbanghang.model.entity.Payment;
import com.example.webbanghang.model.enums.EPaymentType;
import com.example.webbanghang.repository.OrderRepository;
import com.example.webbanghang.repository.PaymentRepository;

import io.github.cdimascio.dotenv.Dotenv;
import vn.payos.PayOS;
import vn.payos.type.CheckoutResponseData;
import vn.payos.type.ItemData;
import vn.payos.type.PaymentData;

public class PaymentService {
    private PaymentRepository paymentRepo;
    private OrderRepository orderRepo;
    public PaymentService(PaymentRepository paymentRepo, OrderRepository orderRepo) {
        this.paymentRepo= paymentRepo;
        this.orderRepo= orderRepo;
    }
    public Payment createCODPayment(Order order) {
        Payment payment = new Payment(order, "VND", "UNPAID", EPaymentType.COD);
        return paymentRepo.save(payment);
    }

    public void markPaymentSuccess(Payment payment) {
        payment.setStatus("SUCCESS");
        paymentRepo.save(payment);
    }
    public String createPaymentLink(Order order)  throws Exception {
        Dotenv dotenv = Dotenv.load();
        String clientId = dotenv.get("PAYOS_CLIENT_ID");
        String apiKey = dotenv.get("PAYOS_API_KEY");
        String checkSumKey = dotenv.get("PAYOS_CHECKSUM_KEY");
        PayOS payOS = new PayOS(clientId, apiKey, checkSumKey);
        List<ItemData> items = new ArrayList<>();
        for(OrderItem item : order.getOrderItems()) {
            items.add(ItemData.builder().name(item.getProductVariant().getName()).quantity(item.getAmount()).price((int)item.getProductVariant().getPrice()).build());
        }
        long currentTimeMillis= System.currentTimeMillis();
        PaymentData paymentData = PaymentData.builder().orderCode(currentTimeMillis).amount((int)order.getTotal()).items(items).returnUrl("http://localhost:8080/unsecure/check_out_success").cancelUrl("http://localhost:8080/unsecure/check_out_cancel").description("Checkout "+order.getId()).build();
        order.setPaymentCode(currentTimeMillis);
        orderRepo.save(order);
        CheckoutResponseData responseData= payOS.createPaymentLink(paymentData);
        return responseData.getCheckoutUrl();
    }
}
