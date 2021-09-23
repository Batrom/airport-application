package pl.bromanowski.airportapplication.domain.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.bromanowski.airportapplication.domain.model.Weight;
import pl.bromanowski.airportapplication.domain.model.WeightUnit;

import java.math.BigDecimal;
import java.math.RoundingMode;

@ExtendWith(MockitoExtension.class)
class WeightUnitConverterTest {
    @Test
    public void convertKilogramToKilogram() {
        final var result = WeightUnitConverter.convert(new Weight(BigDecimal.ONE, WeightUnit.KILOGRAM), WeightUnit.KILOGRAM);
        Assertions.assertEquals(WeightUnit.KILOGRAM, result.unit());
        Assertions.assertEquals(scale(BigDecimal.ONE), result.value());
    }

    @Test
    public void convertPoundToPound() {
        final var result = WeightUnitConverter.convert(new Weight(BigDecimal.ONE, WeightUnit.POUND), WeightUnit.POUND);
        Assertions.assertEquals(WeightUnit.POUND, result.unit());
        Assertions.assertEquals(scale(BigDecimal.ONE), result.value());
    }

    @Test
    public void convertKilogramToPound() {
        final var result = WeightUnitConverter.convert(new Weight(BigDecimal.ONE, WeightUnit.KILOGRAM), WeightUnit.POUND);
        Assertions.assertEquals(WeightUnit.POUND, result.unit());
        Assertions.assertEquals(scale(new BigDecimal("2.20")), result.value());
    }

    @Test
    public void convertPoundToKilogram() {
        final var result = WeightUnitConverter.convert(new Weight(BigDecimal.ONE, WeightUnit.POUND), WeightUnit.KILOGRAM);
        Assertions.assertEquals(WeightUnit.KILOGRAM, result.unit());
        Assertions.assertEquals((new BigDecimal("0.45")), result.value());
    }

    private BigDecimal scale(final BigDecimal value) {
        return value.setScale(2, RoundingMode.HALF_UP);
    }
}