package com.github.ricbau.vendingmachine.domain.ports;

import com.github.ricbau.vendingmachine.domain.entities.Product;
import com.github.ricbau.vendingmachine.domain.usecases.ReadProductUseCase;
import io.vavr.control.Try;

import java.util.Optional;

public interface ProductCrudPort extends ReadProductUseCase {
    Try<Void> persist(Product product);

    Optional<Product> findByID(String productId);

    Try<Void> delete(String id);
}
