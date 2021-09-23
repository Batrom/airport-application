package pl.bromanowski.airportapplication.domain.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.bromanowski.airportapplication.domain.database.model.Airport;

public interface AirportRepository extends JpaRepository<Airport, Long> {

    boolean existsByIataCodeCode(final String iataCode);
}
