package pl.bromanowski.airportapplication.external.controller;

import org.springframework.http.ResponseEntity;

import java.util.Optional;
import java.util.function.Supplier;

abstract class BaseController {
    public <T> ResponseEntity<T> execute(final Supplier<Optional<T>> dataSupplier) {
        try {
            return dataSupplier.get()
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (final Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
