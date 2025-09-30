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

import com.example.webbanghang.service.paymentservice.PaymentService;


@RestController
public class PaymentController {
    private final PaymentService paymentService;
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    } 
    /*@PostMapping("/user/confirm_checkout_online/{orderId}")
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
        JSONObject rawDataObject = json.getJSONObject("data");
        
        String accountNumber = rawDataObject.getString("accountNumber");
        String rawData = rawDataObject.toString();

        
        boolean isValid = paymentService.isValidData(rawData, signature);
        if(isValid&&accountNumber.equals("12345678")) {
            return ResponseEntity.ok("Webhook processed successfully");
        }
        if (isValid) {
            
           JSONObject data= json.getJSONObject("data"); 
            String statusCode = data.getString("code");
            if(statusCode.equals("00")) {
                
            long paymentCode = data.getLong("orderCode");
            String currency = data.getString("currency");
            paymentService.updateDBWhenCheckoutSuccess(paymentCode, currency, "Success");
            }
            System.out.println(rawData);
            return ResponseEntity.ok("Webhook processed successfully");
        } else {
            System.out.println(rawBody);
            
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid signature");
        }
    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
    }
    }
    @GetMapping("/unsecure/pay_os/update_data") 
    public String testConnect() {
        return "Success";
    }
    @PostMapping("/user/confirm_cod/{orderId}") 
    public String confirmCod(@PathVariable int orderId, Authentication auth) {
        String email = auth.getName();
        try {
            paymentService.confirmCODCheckout(email, orderId);
            return "Success";
        }
        catch(Exception e) {
            if(e.getMessage().equals("404")){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
            
            if(e.getMessage().equals("401")) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
            }
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/
    
}
