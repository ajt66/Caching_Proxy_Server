package com.example.caching_server.Controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import reactor.core.publisher.Mono;

import com.example.caching_server.Service.*;

import org.springframework.cache.CacheManager;
import org.springframework.cache.Cache;

@RestController
public class ProductsController {

    private final ProxyService pservice;
    private final CacheManager cacheManager;
    private final Logger logger = Logger.getLogger(ProductsController.class.getName());

    @Autowired
    ProductsController(ProxyService pservice, CacheManager cacheManager) {
        this.pservice = pservice;
        this.cacheManager = cacheManager;
    }

    @SuppressWarnings("null")
    @GetMapping("/{api}")
    public Mono<ResponseEntity<String>> defaultAPICall(@PathVariable String api) {
        String baseURL = "https://dummyjson.com";
        Cache cache = cacheManager.getCache("Cache");
        String result = baseURL + "/" + api;

        if (cache.get(result, String.class) != null) {
            logger.info("Cache hit for Origin url: " + result);
            return Mono.just(cache.get(result, String.class))
                    .map(cachevalue -> {
                        HttpHeaders headers = new HttpHeaders();
                        headers.add("X-Cache-Status", "HIT");
                        return new ResponseEntity<>(cachevalue, headers, HttpStatus.OK);
                    });
        }

        logger.info("Cache miss for Origin url: " + result);
        return pservice.OriginServerCall(result)
                .map(response -> {
                    HttpHeaders headers = new HttpHeaders();
                    headers.add("X-Cache-Status", "MISS");
                    return new ResponseEntity<>(response, headers, HttpStatus.OK);
                });
    }

    @SuppressWarnings("null")
    @GetMapping("/{api}/{id}")
    public Mono<ResponseEntity<String>> idAPICall(@PathVariable String id, @PathVariable String api) {
        String baseURL2 = "https://dummyjson.com";
        Cache cache = cacheManager.getCache("Cache");
        String result = baseURL2 + "/" + api + "/" + id;

        if (cache.get(result, String.class) != null) {
            logger.info("Cache hit for Origin url: " + result);
            return Mono.just(cache.get(result, String.class))
                    .map(cachevalue -> {
                        HttpHeaders headers = new HttpHeaders();
                        headers.add("X-Cache-Status", "HIT");
                        return new ResponseEntity<>(cachevalue, headers, HttpStatus.OK);
                    });
        }

        logger.info("Cache miss for Origin url: " + result);
        return pservice.OriginServerCallWithId(result)
                .map(response -> {
                    HttpHeaders headers = new HttpHeaders();
                    headers.add("X-Cache-Status", "MISS");
                    return new ResponseEntity<>(response, headers, HttpStatus.OK);
                });
    }

}