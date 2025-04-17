package com.example.bargainfindermax.service;

import com.example.bargainfindermax.dto.BfmApiResponse;
import com.example.bargainfindermax.dto.FlightResponse;
import com.example.bargainfindermax.utils.SabreApiClient;
import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import request.BargainRequest;
import response.BargainResponse;

import java.util.List;

@Service
@Data
public class BargainFinderMaxService {

    private static final Logger logger = LogManager.getLogger(BargainFinderMaxService.class);
    private final WebClient.Builder webClientBuilder;
    private final SabreApiClient sabreApiClient;


    @Autowired
    public BargainFinderMaxService(WebClient.Builder webClientBuilder,SabreApiClient sabreApiClient) {
        this.webClientBuilder = webClientBuilder;
        this.sabreApiClient =  sabreApiClient;
    }

    public Mono<BargainResponse> bargainFindMaxShop(BargainRequest request) {
        Mono<BargainResponse>  bargainResponseResponseEntity = sabreApiClient.bargainFindMaxShopAllDetails(getAuthToken(),request);
        return bargainResponseResponseEntity;
    }

    public String getAuthToken() {
        return sabreApiClient.getAuthToken();
    }
    public Mono<List<FlightResponse>> getFlightSegments(String origin, String destination, String departureDate) {
                    WebClient webClient = webClientBuilder.baseUrl("https://api.sabre.com/v2/shop/flights").build();
                   return  webClient.post()
                            .uri(uriBuilder -> uriBuilder.build())
                            .header("Authorization", "Bearer " +  getAuthToken())
                            .header("Content-Type", "application/json")
                            .bodyValue(buildRequestBody(origin, destination, departureDate))
                            .retrieve()
                            .bodyToMono(BfmApiResponse.class)
                            .map(this::mapToFlightResponse);

    }

    private String buildRequestBody(String origin, String destination, String departureDate) {
        return "{ \"origin\": \"" + origin + "\", \"destination\": \"" + destination + "\", \"departureDate\": \"" + departureDate + "\", \"maxSolutions\": 10 }";
    }

    private List<FlightResponse> mapToFlightResponse(BfmApiResponse apiResponse) {
        return apiResponse.getPricedItineraries().stream().map(itinerary -> new FlightResponse(
                1,
                itinerary.getOrigin(),
                itinerary.getDestination(),
                itinerary.getFlightSegments()
        )).toList();
    }

}
