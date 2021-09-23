package pl.bromanowski.airportapplication.domain.database.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Airport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "iata_code_id")
    private IATACode iataCode;

    @OneToMany(mappedBy = "departureAirport")
    private Set<Flight> departingFlights;

    @OneToMany(mappedBy = "arrivalAirport")
    private Set<Flight> arrivingFlights;
}

