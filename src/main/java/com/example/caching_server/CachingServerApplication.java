package com.example.caching_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CachingServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CachingServerApplication.class, args);
	}

}
