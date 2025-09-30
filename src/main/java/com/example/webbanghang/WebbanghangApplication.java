package com.example.webbanghang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode;
import org.springframework.scheduling.annotation.EnableScheduling;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
@EnableCaching
@EnableSpringDataWebSupport(pageSerializationMode = PageSerializationMode.VIA_DTO)
@EnableScheduling
public class WebbanghangApplication {

	public static void main(String[] args) {
		Dotenv dotenv= Dotenv.load();
		SpringApplication.run(WebbanghangApplication.class, args); 
		System.out.println(System.getenv("DATABASE_URL"));
		System.out.println(dotenv.get("PAYOS_CLIENT_ID"));
	}

}
