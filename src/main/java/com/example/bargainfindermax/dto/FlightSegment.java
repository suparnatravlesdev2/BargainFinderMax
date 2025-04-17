package com.example.bargainfindermax.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FlightSegment {
    @JsonProperty("LayoverAirport")
    private String layoverAirport;

    @JsonProperty("ArrivalTime")
    private String arrivalTime;

    @JsonProperty("DepartureTime")
    private String departureTime;

    @JsonProperty("Airlines")
    private String airlines;

    // Getters and Setters
}
