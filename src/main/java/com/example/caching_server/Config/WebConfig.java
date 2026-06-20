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

    /*
     * Bean
     * public LoadingCache<Object, Object> caffeineConfig() {
     * return Caffeine.newBuilder()
     * .maximumSize(10_000)
     * .expireAfterWrite(Duration.ofMinutes(5))
     * .refreshAfterWrite(Duration.ofMinutes(1))
     * .build(key -> createExpensiveGraph(key));
     * }
     * 
     * private String createExpensiveGraph(Object key) {
     * // TODO Auto-generated method stub
     * return "Value for " + key;
     * }
     */

    @Bean
    public Caffeine<Object, Object> caffeineBuilder() {
        return Caffeine.newBuilder()
                .maximumSize(10_000)
                .expireAfterWrite(Duration.ofMinutes(1))
                .recordStats();
    }

    @Bean
    public CacheManager cacheManager(Caffeine<Object, Object> caffeine) {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager("productsCache");
        cacheManager.setCaffeine(caffeine);
        // Enable async cache mode so async/reactive return types can be cached
        cacheManager.setAsyncCacheMode(true);
        return cacheManager;
    }
}
