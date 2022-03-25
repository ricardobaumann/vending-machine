package com.github.ricbau.vendingmachine.domain.usecases;

import com.github.ricbau.vendingmachine.domain.commands.UpdateProductCommand;
import com.github.ricbau.vendingmachine.domain.entities.Product;
import com.github.ricbau.vendingmachine.domain.exceptions.UpdateProductException;
import io.vavr.control.Either;

public interface UpdateProductUseCase {
    Either<UpdateProductException, Product> update(UpdateProductCommand updateProductCommand);
}
