package pl.bromanowski.airportapplication.domain.database.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.bromanowski.airportapplication.domain.model.WeightUnit;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@ToString
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@MappedSuperclass
abstract class Load {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal weight;

    @Enumerated(EnumType.STRING)
    private WeightUnit weightUnit;

    private Long pieces;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flight_id")
    private Flight flight;
}
