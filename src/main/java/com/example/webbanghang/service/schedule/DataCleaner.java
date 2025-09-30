package com.example.webbanghang.service.schedule;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.webbanghang.model.entity.Token;
import com.example.webbanghang.repository.TokenRepository;

@Component
public class DataCleaner {
    private final TokenRepository tokenRepo;
    public DataCleaner(TokenRepository tokenRepo) {
        this.tokenRepo = tokenRepo;
    }
    @Scheduled(fixedRate=1800000) 
    public void cleanToken() {
        Date expire = new Date(System.currentTimeMillis()-TimeUnit.MINUTES.toMillis(30));
        List<Token> listExpiredToken = tokenRepo.findByAddAtBefore(expire);
        tokenRepo.deleteAll(listExpiredToken);
    }
}
