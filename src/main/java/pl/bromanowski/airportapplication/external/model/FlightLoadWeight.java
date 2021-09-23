package pl.bromanowski.airportapplication.external.model;

import lombok.Builder;

import java.io.Serializable;
import java.math.BigDecimal;

public record FlightLoadWeight(
        Weight cargoWeight,
        Weight baggageWeight,
        Weight totalWeight) implements Serializable {

    @Builder
    public FlightLoadWeight {
    }

    public record Weight(
            BigDecimal value,
            String unit) implements Serializable {

        public static Weight from(final pl.bromanowski.airportapplication.domain.model.Weight weight) {
            return new Weight(weight.value(), weight.unit().symbol());
        }
    }
}
