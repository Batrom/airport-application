package pl.bromanowski.airportapplication.external.controller;

import lombok.AllArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.bromanowski.airportapplication.domain.model.WeightUnit;
import pl.bromanowski.airportapplication.domain.service.FlightLoadWeightService;
import pl.bromanowski.airportapplication.external.model.FlightLoadWeight;

import java.time.LocalDate;

@RestController
@RequestMapping("flights")
@AllArgsConstructor
@CommonsLog
public class FlightController extends BaseController {

    private final FlightLoadWeightService flightLoadWeightService;

    @GetMapping("/{flightNumber}/weight")
    public ResponseEntity<FlightLoadWeight> getFlightLoadWeight(@PathVariable("flightNumber") final Long flightNumber,
                                                                @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) final LocalDate date,
                                                                @RequestParam(value = "weightUnit", required = false, defaultValue = "kg") final WeightUnit weightUnit) {
        log.info(String.format("Request for flight load weight - flightNumber: %s, date: %s, weightUnit: %s", flightNumber, date, weightUnit));
        return execute(() -> flightLoadWeightService.calculateFlightLoadWeight(flightNumber, date, weightUnit));
    }
}
