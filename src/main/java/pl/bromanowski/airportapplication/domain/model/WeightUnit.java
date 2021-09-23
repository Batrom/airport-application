package pl.bromanowski.airportapplication.domain.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Accessors(fluent = true)
public enum WeightUnit {
    KILOGRAM("kg"),
    POUND("lb");

    private final String symbol;

    private final static Map<String, WeightUnit> map = initializeMap();

    private static Map<String, WeightUnit> initializeMap() {
        return Arrays.stream(WeightUnit.values())
                .collect(Collectors.toMap(WeightUnit::symbol, Function.identity()));
    }

    public static Optional<WeightUnit> findBySymbol(final String symbol) {
        return Optional.ofNullable(map.get(symbol.toLowerCase()));
    }
}
