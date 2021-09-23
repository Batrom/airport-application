package pl.bromanowski.airportapplication.domain.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.bromanowski.airportapplication.domain.database.model.Cargo;
import pl.bromanowski.airportapplication.domain.model.Weight;

import java.time.LocalDateTime;
import java.util.List;

public interface CargoRepository extends JpaRepository<Cargo, Long> {

    @Query("""
                    SELECT new pl.bromanowski.airportapplication.domain.model.Weight(SUM(c.weight), c.weightUnit)
                    FROM Cargo c
                    WHERE c.flight.number = ?1
                    AND c.flight.departureDate BETWEEN ?2 AND ?3
                    GROUP BY c.weightUnit
            """)
    List<Weight> findFlightLoadWeightsByFlightNumberAndDepartureDate(final Long flightNumber, final LocalDateTime startDate, final LocalDateTime endDate);
}
