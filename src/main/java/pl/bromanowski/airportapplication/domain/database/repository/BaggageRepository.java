package pl.bromanowski.airportapplication.domain.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.bromanowski.airportapplication.domain.database.model.Baggage;
import pl.bromanowski.airportapplication.domain.model.FlightToBaggagePieces;
import pl.bromanowski.airportapplication.domain.model.Weight;

import java.time.LocalDateTime;
import java.util.List;

public interface BaggageRepository extends JpaRepository<Baggage, Long> {

    @Query("""
                    SELECT new pl.bromanowski.airportapplication.domain.model.Weight(SUM(b.weight), b.weightUnit)
                    FROM Baggage b
                    WHERE b.flight.number = ?1
                    AND b.flight.departureDate BETWEEN ?2 AND ?3
                    GROUP BY b.weightUnit
            """)
    List<Weight> findFlightLoadWeightsByFlightNumberAndDepartureDate(final Long flightNumber, final LocalDateTime startDate, final LocalDateTime endDate);

    @Query("""
                    SELECT new pl.bromanowski.airportapplication.domain.model.FlightToBaggagePieces(b.flight.id, SUM(b.pieces))
                    FROM Baggage b
                    WHERE b.flight.departureAirport.iataCode.code = ?1
                    AND b.flight.departureDate BETWEEN ?2 AND ?3
                    GROUP BY b.flight.id
            """)
    List<FlightToBaggagePieces> findDepartingFlightsBaggagePiecesSum(final String iataCode, final LocalDateTime startDate, final LocalDateTime endDate);

    @Query("""
                    SELECT new pl.bromanowski.airportapplication.domain.model.FlightToBaggagePieces(b.flight.id, SUM(b.pieces))
                    FROM Baggage b
                    WHERE b.flight.arrivalAirport.iataCode.code = ?1
                    AND b.flight.departureDate BETWEEN ?2 AND ?3
                    GROUP BY b.flight.id
            """)
    List<FlightToBaggagePieces> findArrivingFlightsBaggagePiecesSum(final String iataCode, final LocalDateTime startDate, final LocalDateTime endDate);
}
