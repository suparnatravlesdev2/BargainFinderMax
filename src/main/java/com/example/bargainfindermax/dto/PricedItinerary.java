package com.example.bargainfindermax.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class PricedItinerary {
    @JsonProperty("Origin")
    private String origin;

    @JsonProperty("Destination")
    private String destination;

    @JsonProperty("FlightSegments")
    private List<FlightSegment> flightSegments;

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public List<FlightSegment> getFlightSegments() {
        return flightSegments;
    }
}

