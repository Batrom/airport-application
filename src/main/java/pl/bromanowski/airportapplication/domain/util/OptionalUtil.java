package pl.bromanowski.airportapplication.domain.util;

import lombok.experimental.UtilityClass;

import java.util.Optional;
import java.util.function.Supplier;

@UtilityClass
public class OptionalUtil {

    public <T> Optional<T> when(final boolean condition, final Supplier<T> thenSupplier) {
        return condition ? Optional.ofNullable(thenSupplier.get()) : Optional.empty();
    }
}
