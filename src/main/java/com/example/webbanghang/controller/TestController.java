package com.example.webbanghang.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.webbanghang.model.onlinepayment.CreatePayUrlHeaders;

@RestController
public class TestController {
    @GetMapping("/unsecure/test_json") 
    public CreatePayUrlHeaders getCreatePayUrlHeaders() {
        return CreatePayUrlHeaders.getInstance();
    }
    @GetMapping("/unsecure/check_out_success") 
    public String checkoutSuccess() {
        return "Success";
    } 
    @GetMapping("/unsecure/check_out_cancel") 
    public String checkoutCancel() {
        return "Fail";
    }

}
