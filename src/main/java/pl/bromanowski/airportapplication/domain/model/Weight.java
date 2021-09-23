package pl.bromanowski.airportapplication.domain.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public record Weight(
        BigDecimal value,
        WeightUnit unit) {

    public Weight(final BigDecimal value, final WeightUnit unit) {
        this.value = scale(value);
        this.unit = unit;
    }

    public Weight(final BigDecimal value, final String symbol) {
        this(scale(value), findWeightUnit(symbol));
    }

    private static WeightUnit findWeightUnit(final String symbol) {
        return WeightUnit.findBySymbol(symbol).orElseThrow(() -> new UnknownWeightUnitSymbolException(symbol));
    }

    public Weight add(final Weight weight) {
        checkIfWeightsHaveEqualWeightUnit(weight);
        return new Weight(this.value.add(weight.value), this.unit);
    }

    private void checkIfWeightsHaveEqualWeightUnit(final Weight weight) {
        if (this.unit != weight.unit) throw new WeightUnitMismatchException(this.unit, weight.unit);
    }

    private static BigDecimal scale(final BigDecimal value) {
        return Objects.nonNull(value) ? value.setScale(2, RoundingMode.HALF_UP) : BigDecimal.ZERO;
    }

    private static class UnknownWeightUnitSymbolException extends RuntimeException {
        public UnknownWeightUnitSymbolException(final String symbol) {
            super(String.format("WeightUnit not found for symbol: %s", symbol));
        }
    }

    private static class WeightUnitMismatchException extends RuntimeException {
        public WeightUnitMismatchException(final WeightUnit weightUnit1, final WeightUnit weightUnit2) {
            super(String.format("Weights have different weight units: %s and %s", weightUnit1, weightUnit2));
        }
    }
}