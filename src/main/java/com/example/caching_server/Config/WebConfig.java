package com.example.caching_server.Config;

import org.springframework.context.annotation.Configuration;

import java.time.Duration;

import com.github.benmanes.caffeine.cache.Caffeine;

import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;

import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebConfig {

    @Bean
    public WebClient.Builder webBuilder() {
        return WebClient.builder();
    }

    @Bean
    public Caffeine<Object, Object> caffeineBuilder() {
        return Caffeine.newBuilder()
                .maximumSize(10_000)
                .expireAfterWrite(Duration.ofMinutes(1))
                .recordStats();
    }

    @Bean
    public CacheManager cacheManager(Caffeine<Object, Object> caffeine) {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager("Cache");
        cacheManager.setCaffeine(caffeine);
        // Enable async cache mode so async/reactive return types can be cached
        cacheManager.setAsyncCacheMode(true);
        return cacheManager;
    }
}
