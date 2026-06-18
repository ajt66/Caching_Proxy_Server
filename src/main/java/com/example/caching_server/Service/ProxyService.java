package com.example.caching_server.Service;

import com.example.caching_server.Config.*;
//import com.example.caching_server.model.WeatherResponse;
import com.example.caching_server.model.products;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;
import org.springframework.core.env.Environment;

//import redis.clients.jedis.UnifiedJedis;

@Service
public class ProxyService {
    private final WebClient.Builder webBuilder;
    private Environment environment;
    // private final UnifiedJedis jedis;

    @Autowired
    public ProxyService(WebClient.Builder webBuilder, Environment environment) {
        this.webBuilder = webBuilder;
        this.environment = environment;
    }

    @SuppressWarnings("null")
    public Mono<String> defaultAPICall(String url, String id) {
        try {
            return webBuilder
                    .baseUrl(url)
                    .build()
                    .get()
                    .uri(uriBuilder -> uriBuilder
                            .pathSegment(id)
                            .queryParam("select", "id,title,description,category,price")
                            .build())
                    .retrieve()
                    .onStatus(status -> status.isError(), clientResponse -> clientResponse.bodyToMono(String.class)
                            .defaultIfEmpty("<no response body>")
                            .flatMap(body -> Mono.error(new RuntimeException(
                                    "Origin Server API failed with status " + clientResponse.statusCode() + ": "
                                            + body))))
                    .bodyToMono(String.class);
        } catch (Exception e) {
            throw new RuntimeException("Weather API call failed: " + e.getMessage(), e);
        }
    }

}
