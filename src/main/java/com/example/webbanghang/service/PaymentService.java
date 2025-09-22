package com.example.webbanghang.service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.digest.HmacUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.example.webbanghang.model.entity.Order;
import com.example.webbanghang.model.entity.OrderItem;
import com.example.webbanghang.model.entity.Payment;
import com.example.webbanghang.model.entity.User;
import com.example.webbanghang.model.enums.EPaymentType;
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
        return createPaymentLink(order);
    }
    public String createPaymentLink( Order order) throws Exception {
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
    private Iterator<String> sortedIterator(Iterator<?> it, Comparator<String> comparator) {
            List<String> list = new ArrayList<String>();
            while (it.hasNext()) {
                list.add((String) it.next());
            }

            Collections.sort(list, comparator);
            return list.iterator();
    } 
    public boolean isValidData(String transaction, String transactionSignature) {
        Dotenv dotenv = Dotenv.load();
        String checkSumKey = dotenv.get("PAYOS_CHECKSUM_KEY");
            try {
                JSONObject jsonObject = new JSONObject(transaction);
                Iterator<String> sortedIt = sortedIterator(jsonObject.keys(), (a, b) -> a.compareTo(b));

                StringBuilder transactionStr = new StringBuilder();
                while (sortedIt.hasNext()) {
                    String key = sortedIt.next();
                    String value = jsonObject.get(key).toString();
                    transactionStr.append(key);
                    transactionStr.append('=');
                    transactionStr.append(value);
                    if (sortedIt.hasNext()) {
                        transactionStr.append('&');
                    }
                }

                String signature = new HmacUtils("HmacSHA256", checkSumKey).hmacHex(transactionStr.toString());
                return signature.equals(transactionSignature);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }
    public boolean updateDBWhenCheckoutSuccess(long paymentCode, String currency, String status) throws Exception {
        
        Order order = orderRepo.findFirstByPaymentCode(paymentCode);
        if(order==null) {
            return false;
        }
        paymentRepo.save(new Payment(order, currency,status,EPaymentType.ONLINE));
        return true;
        
    }
    public void confirmCODCheckout(String email, int orderId) throws Exception { 
        Order order = orderRepo.findById(orderId).orElse(null);
        if(order==null) {
            throw new Exception("404");
        }
        User user =(User) userService.loadUserByUsername(email);
        if(order.getUser().getId()!=user.getId()) {
            throw new Exception("401");
        } 
        Payment payment = new Payment(order,"VND","UNPAIED",EPaymentType.COD);
        paymentRepo.save(payment);
        
    }
}
