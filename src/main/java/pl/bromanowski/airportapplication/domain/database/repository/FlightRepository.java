package pl.bromanowski.airportapplication.domain.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.bromanowski.airportapplication.domain.database.model.Flight;

import java.time.LocalDateTime;

public interface FlightRepository extends JpaRepository<Flight, Long> {

    boolean existsByNumberAndDepartureDateBetween(final Long flightNumber, final LocalDateTime startDate, final LocalDateTime endDate);
}
