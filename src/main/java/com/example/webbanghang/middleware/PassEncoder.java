package com.example.webbanghang.middleware;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PassEncoder {
    private static PasswordEncoder passEncoder = new BCryptPasswordEncoder(12);
    public static PasswordEncoder getPassEncoder() {
        return PassEncoder.passEncoder;
    } 
    public static boolean isPasswordMatch(String hashedPassword, String plainPassword) {
        return passEncoder.matches(hashedPassword, plainPassword);
    }
    public static String hashPassword(String plainPassword) {
        return passEncoder.encode(plainPassword);
    }
}
