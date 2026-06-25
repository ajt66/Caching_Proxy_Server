package com.example.caching_server.Service;

import com.example.caching_server.Config.*;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;

import reactor.core.publisher.Mono;

@Service
public class ProxyService {
    private final WebClient.Builder webBuilder;

    @Autowired
    public ProxyService(WebClient.Builder webBuilder) {
        this.webBuilder = webBuilder;
    }

    @SuppressWarnings("null")
    @Cacheable(value = "Cache", key = "#result")
    public Mono<String> OriginServerCall(String result) {
        try {
            return webBuilder.build()
                    .get()
                    .uri(result)
                    .retrieve()
                    .onStatus(status -> status.isError(), clientResponse -> clientResponse.bodyToMono(String.class)
                            .defaultIfEmpty("<no response body>")
                            .flatMap(body -> Mono.error(new RuntimeException(
                                    "Origin Server API failed with status " + clientResponse.statusCode() + ": "
                                            + body))))
                    .bodyToMono(String.class);
        } catch (Exception e) {
            throw new RuntimeException("Origin Server API call failed: " + e.getMessage(), e);
        }
    }

    @SuppressWarnings("null")
    @Cacheable(value = "Cache", key = "#result")
    public Mono<String> OriginServerCallWithId(String result) {
        try {
            return webBuilder
                    .build()
                    .get()
                    .uri(result)
                    .retrieve()
                    .onStatus(status -> status.isError(), clientResponse -> clientResponse.bodyToMono(String.class)
                            .defaultIfEmpty("<no response body>")
                            .flatMap(body -> Mono.error(new RuntimeException(
                                    "Origin Server API failed with status " + clientResponse.statusCode() + ": "
                                            + body))))
                    .bodyToMono(String.class);
        } catch (Exception e) {
            throw new RuntimeException("Origin Server API call failed: " + e.getMessage(), e);
        }
    }
}
