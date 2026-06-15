package com.example.caching_server.Service;

import com.example.caching_server.Config.*;
//import com.example.caching_server.model.WeatherResponse;

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
    public WeatherTrackerService(WebClient.Builder webBuilder, Environment environment) {
        this.webBuilder = webBuilder;
        this.environment = environment;
    }

    public WeatherResponse dynamicAPICall(String url, String locationParam, String rangeParam,
            String unitGroupParam) {
        String endpoint;
        if (rangeParam.equals("15days")) {
            endpoint = locationParam;
        } else {
            endpoint = locationParam + "/" + rangeParam;
        }

        try {
            return webBuilder
                    .baseUrl(url)
                    .build()
                    .get()
                    .uri(uriBuilder -> uriBuilder
                            .path(endpoint)
                            .queryParam("unitGroup", unitGroupParam)
                            .queryParam("key", environment.getProperty("env.data.one"))
                            .queryParam("contentType", "json")
                            .build())
                    .retrieve()
                    .onStatus(status -> status.isError(), clientResponse -> clientResponse.bodyToMono(String.class)
                            .defaultIfEmpty("<no response body>")
                            .flatMap(body -> Mono.error(new RuntimeException(
                                    "VisualCrossing API failed with status " + clientResponse.statusCode() + ": "
                                            + body))))
                    .bodyToMono(WeatherResponse.class)
                    .block();
        } catch (Exception e) {
            throw new RuntimeException("Weather API call failed: " + e.getMessage(), e);
        }
    }

    public WeatherResponse customAPICall(String url, String locationParam, String date1Param, String date2Param,
            String unitGroupParam) {
        String endpoint = locationParam + "/" + date1Param + "/" + date2Param;
        try {
            return webBuilder
                    .baseUrl(url)
                    .build()
                    .get()
                    .uri(uriBuilder -> uriBuilder
                            .path(endpoint)
                            .queryParam("unitGroup", unitGroupParam)
                            .queryParam("key", environment.getProperty("env.data.one"))
                            .queryParam("contentType", "json")
                            .build())
                    .retrieve()
                    .onStatus(status -> status.isError(), clientResponse -> clientResponse.bodyToMono(String.class)
                            .defaultIfEmpty("<no response body>")
                            .flatMap(body -> Mono.error(new RuntimeException(
                                    "VisualCrossing API failed with status " + clientResponse.statusCode() + ": "
                                            + body))))
                    .bodyToMono(WeatherResponse.class)
                    .block();
        } catch (Exception e) {
            throw new RuntimeException("Weather API call failed: " + e.getMessage(), e);
        }
    }
}
