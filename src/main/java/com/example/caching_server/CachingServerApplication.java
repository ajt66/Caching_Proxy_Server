package com.example.caching_server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import picocli.CommandLine;
import picocli.CommandLine.IFactory;

@SpringBootApplication
@EnableCaching
public class CachingServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CachingServerApplication.class, args);
	}

}
