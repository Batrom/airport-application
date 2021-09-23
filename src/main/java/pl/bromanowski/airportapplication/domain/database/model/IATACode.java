package pl.bromanowski.airportapplication.domain.database.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "iata_code")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class IATACode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    @OneToMany(mappedBy = "iataCode")
    private Set<Airport> airports;
}
