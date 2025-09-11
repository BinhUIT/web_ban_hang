package com.example.webbanghang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode;

@SpringBootApplication
@EnableSpringDataWebSupport(pageSerializationMode = PageSerializationMode.VIA_DTO)
public class WebbanghangApplication {

	public static void main(String[] args) {
		
		SpringApplication.run(WebbanghangApplication.class, args); 
		System.out.println(System.getenv("DATABASE_URL"));
	}

}
