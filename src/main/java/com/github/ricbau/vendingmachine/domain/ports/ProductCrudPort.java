package com.github.ricbau.vendingmachine.domain.ports;

import com.github.ricbau.vendingmachine.domain.entities.Product;
import io.vavr.control.Try;

public interface ProductCrudPort {
    Try<Void> persist(Product product);
}
