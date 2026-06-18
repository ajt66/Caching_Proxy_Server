package com.example.caching_server.Controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import reactor.core.publisher.Mono;

import com.example.caching_server.Service.*;

@RestController
@RequestMapping("/products")
public class ProductsController {

    private final ProxyService pservice;
    private final Logger logger = Logger.getLogger(ProductsController.class.getName());

    @Autowired
    ProductsController(ProxyService pservice) {
        this.pservice = pservice;
    }

    @GetMapping("/{id}")
    public Mono<String> idAPICall(@PathVariable String id) {
        String baseAPI = "https://dummyjson.com/products";
        return pservice.defaultAPICall(baseAPI, id);
    }

}