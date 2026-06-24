package com.example.caching_server.Controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
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
    @GetMapping("/{api}/{id}")
    public Mono<ResponseEntity<String>> idAPICall(@PathVariable String id, @PathVariable String api) {
        String baseAPI = "https://dummyjson.com/products";
        Cache cache = cacheManager.getCache("productsCache");

        if (cache.get(id, String.class) != null) {
            return Mono.just(cache.get(id, String.class))
                    .map(cachevalue -> {
                        HttpHeaders headers = new HttpHeaders();
                        headers.add("X-Cache-Status", "HIT");
                        return new ResponseEntity<>(cachevalue, headers, HttpStatus.OK);
                    });
        }

        return pservice.defaultAPICall(baseAPI, id)
                .map(response -> {
                    HttpHeaders headers = new HttpHeaders();
                    headers.add("X-Cache-Status", "MISS");
                    return new ResponseEntity<>(response, headers, HttpStatus.OK);
                });
    }

}