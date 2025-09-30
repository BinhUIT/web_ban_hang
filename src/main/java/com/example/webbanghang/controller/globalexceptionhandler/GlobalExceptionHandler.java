package com.example.webbanghang.controller.globalexceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import com.example.webbanghang.model.response.Response;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RuntimeException.class) 
    public ResponseEntity<Response> handleException(Exception e) {
        ResponseStatusException response = com.example.webbanghang.middleware.ExceptionHandler.getResponseStatusException(e);
        return new ResponseEntity(new Response(response.getMessage(),null,response.getStatusCode().value()), HttpStatus.valueOf(response.getStatusCode().value()));
    }
}
