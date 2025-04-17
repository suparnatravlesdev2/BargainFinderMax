package com.example.bargainfindermax.controller;

import com.example.bargainfindermax.dto.FlightResponse;
import com.example.bargainfindermax.service.BargainFinderMaxService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import request.BargainRequest;
import response.BargainResponse;

import java.util.List;

@RestController
@RequestMapping("/v5/offers")
public class BargainFinderMaxController {

    private final BargainFinderMaxService service;

    public BargainFinderMaxController(BargainFinderMaxService service) {
        this.service = service;
    }

    @GetMapping("/testServer")
    public String testServer() {
        return "Server up and Running ";
    }

    @GetMapping("/auth-token")
    public String getAuthToken() {
        return service.getAuthToken();
    }

    @PostMapping("/shop")
    public Mono<BargainResponse> bargainFindMaxShop(@RequestBody BargainRequest request) {
        return service.bargainFindMaxShop(request);
    }

    @GetMapping("/search")
    public Mono<List<FlightResponse>> searchFlights(
            @RequestParam String origin,
            @RequestParam String destination,
            @RequestParam String departureDate) {
        return service.getFlightSegments(origin, destination, departureDate);
    }

}
