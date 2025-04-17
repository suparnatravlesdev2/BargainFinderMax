package com.example.bargainfindermax.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class BfmApiResponse {
    @JsonProperty("PricedItineraries")
    private List<PricedItinerary> pricedItineraries;

    public List<PricedItinerary> getPricedItineraries() {
        return pricedItineraries;
    }
}
