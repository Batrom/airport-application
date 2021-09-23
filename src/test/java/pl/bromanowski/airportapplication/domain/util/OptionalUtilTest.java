package pl.bromanowski.airportapplication.domain.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OptionalUtilTest {

    @Test
    public void shouldReturnEmptyOptional() {
        final var result = OptionalUtil.when(false, () -> 1);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void shouldReturnNotEmptyOptional() {
        final var result = OptionalUtil.when(true, () -> 1);
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(1, result.get());
    }
}