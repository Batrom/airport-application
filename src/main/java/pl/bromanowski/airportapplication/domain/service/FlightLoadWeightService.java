package pl.bromanowski.airportapplication.domain.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.bromanowski.airportapplication.domain.database.repository.BaggageRepository;
import pl.bromanowski.airportapplication.domain.database.repository.CargoRepository;
import pl.bromanowski.airportapplication.domain.database.repository.FlightRepository;
import pl.bromanowski.airportapplication.domain.model.Weight;
import pl.bromanowski.airportapplication.domain.model.WeightUnit;
import pl.bromanowski.airportapplication.domain.util.OptionalUtil;
import pl.bromanowski.airportapplication.domain.util.WeightUnitConverter;
import pl.bromanowski.airportapplication.external.model.FlightLoadWeight;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

@Service
@AllArgsConstructor
public class FlightLoadWeightService {

    private final BaggageRepository baggageRepository;
    private final CargoRepository cargoRepository;
    private final FlightRepository flightRepository;

    public Optional<FlightLoadWeight> calculateFlightLoadWeight(final Long flightNumber, final LocalDate departureDate, final WeightUnit weightUnit) {
        return OptionalUtil.when(flightExists(flightNumber, departureDate), () -> calculate(flightNumber, departureDate, weightUnit));
    }

    private FlightLoadWeight calculate(final Long flightNumber, final LocalDate departureDate, final WeightUnit weightUnit) {
        final var baggageWeight = calculateBaggageWeight(flightNumber, departureDate, weightUnit);
        final var cargoWeight = calculateCargoWeight(flightNumber, departureDate, weightUnit);
        final var totalWeight = baggageWeight.add(cargoWeight);

        return FlightLoadWeight.builder()
                .cargoWeight(FlightLoadWeight.Weight.from(cargoWeight))
                .baggageWeight(FlightLoadWeight.Weight.from(baggageWeight))
                .totalWeight(FlightLoadWeight.Weight.from(totalWeight))
                .build();
    }

    private boolean flightExists(final Long flightNumber, final LocalDate departureDate) {
        return flightRepository.existsByNumberAndDepartureDateBetween(flightNumber, departureDate.atStartOfDay(), departureDate.plusDays(1).atStartOfDay());
    }

    private Weight calculateBaggageWeight(final Long flightNumber, final LocalDate departureDate, final WeightUnit weightUnit) {
        return calculateWeight(() -> baggageRepository.findFlightLoadWeightsByFlightNumberAndDepartureDate(flightNumber, departureDate.atStartOfDay(), departureDate.plusDays(1).atStartOfDay()), weightUnit);
    }

    private Weight calculateCargoWeight(final Long flightNumber, final LocalDate departureDate, final WeightUnit weightUnit) {
        return calculateWeight(() -> cargoRepository.findFlightLoadWeightsByFlightNumberAndDepartureDate(flightNumber, departureDate.atStartOfDay(), departureDate.plusDays(1).atStartOfDay()), weightUnit);
    }

    private Weight calculateWeight(final Supplier<List<Weight>> weightsSupplier, final WeightUnit weightUnit) {
        return weightsSupplier.get()
                .stream()
                .map(weight -> WeightUnitConverter.convert(weight, weightUnit))
                .reduce(Weight::add)
                .orElseGet(() -> new Weight(BigDecimal.ZERO, weightUnit));
    }
}
