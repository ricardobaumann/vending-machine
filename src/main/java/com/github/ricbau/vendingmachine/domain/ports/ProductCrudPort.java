package com.github.ricbau.vendingmachine.domain.ports;

import com.github.ricbau.vendingmachine.domain.entities.Product;
import io.vavr.control.Try;

import java.util.Optional;

public interface ProductCrudPort {
    Try<Void> persist(Product product);

    Optional<Product> findByID(String productId);
}
