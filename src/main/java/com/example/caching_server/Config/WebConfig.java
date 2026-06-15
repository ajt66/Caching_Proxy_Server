package com.example.caching_server.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebConfig {

    @Bean
    public WebClient.Builder webBuilder() {
        return WebClient.builder();
    }

}
