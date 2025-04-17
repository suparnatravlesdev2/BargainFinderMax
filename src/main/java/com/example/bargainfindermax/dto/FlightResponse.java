package com.example.bargainfindermax.dto;

import java.util.List;

public class FlightResponse {
    private int id;
    private String OriginLocation;
    private String DestinationLocation;
    private List<FlightSegment> FlightSegment;

    public FlightResponse(int id, String origin, String destination, List<FlightSegment> segments) {
        this.id = id;
        this.OriginLocation = origin;
        this.DestinationLocation = destination;
        this.FlightSegment = segments;
    }

    // Getters and Setters
}
