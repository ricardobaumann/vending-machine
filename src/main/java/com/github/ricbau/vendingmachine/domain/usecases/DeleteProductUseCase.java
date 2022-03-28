package com.github.ricbau.vendingmachine.domain.usecases;

import com.github.ricbau.vendingmachine.domain.exceptions.DeleteProductException;
import io.vavr.control.Either;

public interface DeleteProductUseCase {
    Either<DeleteProductException, Void> delete(String id);
}
