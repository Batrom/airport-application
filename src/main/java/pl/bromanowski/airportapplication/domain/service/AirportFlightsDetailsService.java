package pl.bromanowski.airportapplication.domain.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.bromanowski.airportapplication.domain.database.repository.AirportRepository;
import pl.bromanowski.airportapplication.domain.database.repository.BaggageRepository;
import pl.bromanowski.airportapplication.domain.model.FlightToBaggagePieces;
import pl.bromanowski.airportapplication.domain.util.OptionalUtil;
import pl.bromanowski.airportapplication.external.model.AirportFlightsDetails;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

@Service
@AllArgsConstructor
public class AirportFlightsDetailsService {

    private final BaggageRepository baggageRepository;
    private final AirportRepository airportRepository;

    public Optional<AirportFlightsDetails> findAirportFlightsDetails(final String iataCode, final LocalDate date) {
        return OptionalUtil.when(airportExists(iataCode), () -> findDetails(iataCode, date));
    }

    private boolean airportExists(final String iataCode) {
        return airportRepository.existsByIataCodeCode(iataCode);
    }

    private AirportFlightsDetails findDetails(final String iataCode, final LocalDate date) {
        final var departingFlightsDetails = createFlightsDetails(() -> baggageRepository.findDepartingFlightsBaggagePiecesSum(iataCode, date.atStartOfDay(), date.plusDays(1).atStartOfDay()));
        final var arrivingFlightsDetails = createFlightsDetails(() -> baggageRepository.findArrivingFlightsBaggagePiecesSum(iataCode, date.atStartOfDay(), date.plusDays(1).atStartOfDay()));

        return new AirportFlightsDetails(departingFlightsDetails, arrivingFlightsDetails);
    }

    private AirportFlightsDetails.Details createFlightsDetails(final Supplier<List<FlightToBaggagePieces>> baggagePiecesSupplier) {
        final var flightToBaggagePiecesList = baggagePiecesSupplier.get();
        final var numberOfBaggagePieces = flightToBaggagePiecesList.stream()
                .mapToLong(FlightToBaggagePieces::piecesCount)
                .sum();
        return new AirportFlightsDetails.Details((long) flightToBaggagePiecesList.size(), numberOfBaggagePieces);
    }
}
