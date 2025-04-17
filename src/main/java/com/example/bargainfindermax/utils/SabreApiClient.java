package com.example.bargainfindermax.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import request.BargainRequest;
import response.BargainResponse;

import java.util.Map;


@Service
public class SabreApiClient {

    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    @Value("${sabre.api.url}")
    private String apiUrl;
    @Value("${sabre.api.client_id}")
    private String clientId;
    @Value("${sabre.api.client_secret}")
    private String clientSecret;

    public SabreApiClient(WebClient.Builder webClientBuilder, ObjectMapper objectMapper) {
        this.webClient = webClientBuilder
                .baseUrl("https://api.cert.platform.sabre.com")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
        this.objectMapper = objectMapper;
    }

    public String getAuthToken() {
        return webClient.post()
                .uri("/v2/auth/token")
                .header(HttpHeaders.AUTHORIZATION, "Basic " + "VmpFNmRtY3hiSE4wWkhSNFlYUm1ZbkU0TmpwRVJWWkRSVTVVUlZJNlJWaFU6VUd4NE9FeHFXVEk9")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .body(BodyInserters.fromFormData("grant_type", "client_credentials")) // ✅ Use form-data
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> "" + response.get("access_token"))
                .block(); // Blocking for simplicity
    }

    public Mono<BargainResponse> bargainFindMaxShopAllDetails(String bearerToken, BargainRequest bargainRequest) {
        try {
            String requestBody = objectMapper.writeValueAsString(bargainRequest);

            return webClient.post()
                    .uri("/v5/offers/shop")
                    .headers(headers -> {
                        headers.setBearerAuth(bearerToken);
                        headers.setContentType(MediaType.APPLICATION_JSON);
                    })
                    .bodyValue(bargainRequest)
                    .retrieve()
                    .onStatus(status -> status.value() >= 400 && status.value() < 500, clientResponse -> {
                        return clientResponse.bodyToMono(String.class)
                                .flatMap(errorBody -> Mono.error(new RuntimeException("Client error: " + errorBody)));
                    })
                    .bodyToMono(BargainResponse.class);

        } catch (JsonProcessingException e) {
            return Mono.error(new RuntimeException("Error serializing request", e));
        }
    }
}
