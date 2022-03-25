package com.github.ricbau.vendingmachine.domain.usecases;

import com.github.ricbau.vendingmachine.domain.commands.CreateProductCommand;
import com.github.ricbau.vendingmachine.domain.entities.Product;
import com.github.ricbau.vendingmachine.domain.exceptions.CreateProductException;
import io.vavr.control.Either;

public interface CreateProductUseCase {
    Either<CreateProductException, Product> create(CreateProductCommand createProductCommand);
}
