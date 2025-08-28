package com.example.webbanghang.middleware;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextHolder {
    private final ApplicationContext context; 
    private static ApplicationContextHolder instance;
    public ApplicationContextHolder(ApplicationContext context) {
        if(ApplicationContextHolder.instance==null) {
            ApplicationContextHolder.instance = this;
        }
        this.context= context; 
    }
    public static ApplicationContext getContext() {
        return ApplicationContextHolder.instance.context;
    }
}
