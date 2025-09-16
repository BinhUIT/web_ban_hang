package com.example.webbanghang.controller;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.webbanghang.model.entity.Payment;
import com.example.webbanghang.service.PaymentService;

import io.github.cdimascio.dotenv.Dotenv;

import vn.payos.type.WebhookData;


@RestController
public class PaymentController {
    private final PaymentService paymentService;
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    } 
    @PostMapping("/user/confirm_checkout/{orderId}")
    public String getPayURL(Authentication auth, @PathVariable int orderId) {
        String email = auth.getName();
        try {
            return paymentService.createPaymentLink(email, orderId);
        }
        catch(Exception e) {
             if(e.getMessage().equals("404")){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
            if(e.getMessage().equals("400")) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
            if(e.getMessage().equals("401")) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
            }
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/unsecure/pay_os/update_data") 
    public ResponseEntity<String> validAndUpdateDB(@RequestBody String rawBody) {
        
        try {
        
        JSONObject json = new JSONObject(rawBody);
        String signature = json.getString("signature");

       
        json.remove("signature");
        String rawData = json.toString();

        
        boolean isValid = paymentService.validateCheckSum(rawData, signature);

        if (isValid) {
            
           JSONObject data= json.getJSONObject("data"); 
            String statusCode = data.getString("code");
            if(statusCode.equals("00")) {
                
            int orderCode = (int) data.getLong("orderCode");
            String currency = data.getString("currency");
            paymentService.updateDBWhenCheckoutSuccess(orderCode, currency, "Success");
            }
            return ResponseEntity.ok("Webhook processed successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid signature");
        }
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
    }
    }
    @GetMapping("/unsecure/pay_os/update_data") 
    public String testConnect() {
        return "Success";
    }
    
}
