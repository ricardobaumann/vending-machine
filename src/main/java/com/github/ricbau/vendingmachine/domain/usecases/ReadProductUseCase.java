package com.github.ricbau.vendingmachine.domain.usecases;

import com.github.ricbau.vendingmachine.domain.entities.Product;

import java.util.List;
import java.util.Optional;

public interface ReadProductUseCase {
    Optional<Product> findById(String id);

    List<Product> getAll();
}
