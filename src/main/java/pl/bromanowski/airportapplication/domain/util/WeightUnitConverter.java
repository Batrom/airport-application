package pl.bromanowski.airportapplication.domain.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.experimental.UtilityClass;
import pl.bromanowski.airportapplication.domain.model.Weight;
import pl.bromanowski.airportapplication.domain.model.WeightUnit;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@UtilityClass
public class WeightUnitConverter {

    public Weight convert(final Weight weight, final WeightUnit targetWeightUnit) {
        return ConversionType.findRatio(weight.unit(), targetWeightUnit)
                .map(ratio -> new Weight(ratio.multiply(weight.value()), targetWeightUnit))
                .orElseThrow(() -> new ConversionTypeNotFoundException(weight.unit(), targetWeightUnit));
    }

    @AllArgsConstructor
    @Getter
    @Accessors(fluent = true)
    private enum ConversionType {
        KILOGRAM_TO_POUND(WeightUnit.KILOGRAM, WeightUnit.POUND, new BigDecimal("2.20462262")),
        KILOGRAM_TO_KILOGRAM(WeightUnit.KILOGRAM, WeightUnit.KILOGRAM, BigDecimal.ONE),
        POUND_TO_KILOGRAM(WeightUnit.POUND, WeightUnit.KILOGRAM, new BigDecimal("0.45359237")),
        POUND_TO_POUND(WeightUnit.POUND, WeightUnit.POUND, BigDecimal.ONE);

        private static Optional<BigDecimal> findRatio(final WeightUnit from, final WeightUnit to) {
            return Optional.ofNullable(map.get(new WeightUnitPair(from, to))).map(ConversionType::ratio);
        }

        private final static Map<WeightUnitPair, ConversionType> map = initializeMap();

        private static Map<WeightUnitPair, ConversionType> initializeMap() {
            return Arrays.stream(ConversionType.values())
                    .collect(Collectors.toMap(WeightUnitPair::from, Function.identity()));
        }

        private final WeightUnit from;
        private final WeightUnit to;
        private final BigDecimal ratio;

        private static record WeightUnitPair(WeightUnit from, WeightUnit to) {
            private static WeightUnitPair from(final ConversionType conversionType) {
                return new WeightUnitPair(conversionType.from, conversionType.to);
            }
        }
    }

    private static class ConversionTypeNotFoundException extends RuntimeException {
        public ConversionTypeNotFoundException(final WeightUnit from, final WeightUnit to) {
            super(String.format("ConversionType not found for conversion from %s to %s", from, to));
        }
    }
}
