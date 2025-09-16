package com.example.webbanghang.service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Service;

import com.example.webbanghang.model.entity.Order;
import com.example.webbanghang.model.entity.OrderItem;
import com.example.webbanghang.model.entity.Payment;
import com.example.webbanghang.model.entity.User;
import com.example.webbanghang.repository.OrderRepository;
import com.example.webbanghang.repository.PaymentRepository;

import io.github.cdimascio.dotenv.Dotenv;
import vn.payos.PayOS;
import vn.payos.type.CheckoutResponseData;
import vn.payos.type.ItemData;
import vn.payos.type.PaymentData;

@Service
public class PaymentService {
    private final UserService userService;
    private final OrderRepository orderRepo;
    private final PaymentRepository paymentRepo;
    public PaymentService(UserService userService, OrderRepository orderRepo, PaymentRepository paymentRepo) {
        this.userService= userService;
        this.orderRepo = orderRepo;
        this.paymentRepo = paymentRepo;
    }
    public String createPaymentLink(String email, int orderId) throws Exception{
        User user = (User) userService.loadUserByUsername(email);
        Order order = orderRepo.findById(orderId).orElse(null);
        if(order==null) {
            throw new Exception("404");
        } 
        if(order.getIsPaid()) {
            throw new Exception("400");
        } 
        if(user.getId()!=order.getUser().getId()) {
            throw new Exception("401");
        }
        Dotenv dotenv = Dotenv.load();
        String clientId = dotenv.get("PAYOS_CLIENT_ID");
        String apiKey = dotenv.get("PAYOS_API_KEY");
        String checkSumKey = dotenv.get("PAYOS_CHECKSUM_KEY");
        PayOS payOS = new PayOS(clientId, apiKey, checkSumKey);
        List<ItemData> items = new ArrayList<>();
        for(OrderItem item : order.getOrderItems()) {
            items.add(ItemData.builder().name(item.getProductVariant().getName()).quantity(item.getAmount()).price((int)item.getProductVariant().getPrice()).build());
        }
        PaymentData paymentData = PaymentData.builder().orderCode((long)order.getId()).amount((int)order.getTotal()).items(items).returnUrl("http://localhost:8080/unsecure/check_out_success").cancelUrl("http://localhost:8080/unsecure/check_out_cancel").description("Checkout "+order.getId()).build();
        CheckoutResponseData responseData= payOS.createPaymentLink(paymentData);
        return responseData.getCheckoutUrl();
    }
    public boolean validateCheckSum(String rawData, String signature){
         Dotenv dotenv = Dotenv.load();
         String checkSumKey = dotenv.get("PAYOS_CHECKSUM_KEY");
         try {
        
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(checkSumKey.getBytes("UTF-8"), "HmacSHA256");
            sha256_HMAC.init(secret_key);

            
            byte[] hashBytes = sha256_HMAC.doFinal(rawData.getBytes("UTF-8"));

            
            String expectedSignature = Base64.getEncoder().encodeToString(hashBytes);

            
            return expectedSignature.equals(signature);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }
    public boolean updateDBWhenCheckoutSuccess(int orderId, String currency, String status) throws Exception {
        
        Order order = orderRepo.findById(orderId).orElse(null);
        if(order==null) {
            return false;
        }
        paymentRepo.save(new Payment(order, currency,status));
        return true;
        
    }
}
