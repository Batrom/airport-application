package pl.bromanowski.airportapplication.external.controller;

import lombok.AllArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.bromanowski.airportapplication.domain.service.AirportFlightsDetailsService;
import pl.bromanowski.airportapplication.external.model.AirportFlightsDetails;

import java.time.LocalDate;

@RestController
@AllArgsConstructor
@RequestMapping("airports")
@CommonsLog
public class AirportController extends BaseController {

    private final AirportFlightsDetailsService airportFlightsDetailsService;

    @GetMapping("/{iataCode}/flights")
    public ResponseEntity<AirportFlightsDetails> getAirportFlightsDetails(@PathVariable("iataCode") final String iataCode,
                                                                          @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) final LocalDate date) {
        log.info(String.format("Request for airport flights details - iataCode: %s, date: %s", iataCode, date));
        return execute(() -> airportFlightsDetailsService.findAirportFlightsDetails(iataCode, date));
    }
}
