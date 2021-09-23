package pl.bromanowski.airportapplication.external.model;

import java.io.Serializable;

public record AirportFlightsDetails(
        Details departingFlights,
        Details arrivingFlights) implements Serializable {

    public record Details(Long numberOfFlights,
                          Long numberOfBaggagePieces) implements Serializable {
    }
}
