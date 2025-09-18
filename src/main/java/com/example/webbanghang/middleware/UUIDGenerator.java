package com.example.webbanghang.middleware;

import java.util.UUID;

public class UUIDGenerator {
    public static String getRanDomUUID() {
        String uuidString = UUID.randomUUID().toString().replace("-", "");
        return uuidString.substring(0,12); 
    }
}
