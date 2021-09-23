package pl.bromanowski.airportapplication.external.converter;

import org.springframework.core.convert.converter.Converter;
import pl.bromanowski.airportapplication.domain.model.WeightUnit;

public class StringToWeightUnitConverter implements Converter<String, WeightUnit> {
    @Override
    public WeightUnit convert(final String weightUnitSymbol) {
        return WeightUnit.findBySymbol(weightUnitSymbol).orElseThrow(IllegalArgumentException::new);
    }
}