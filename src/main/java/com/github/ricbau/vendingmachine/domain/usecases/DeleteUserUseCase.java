package com.github.ricbau.vendingmachine.domain.usecases;

import com.github.ricbau.vendingmachine.domain.exceptions.DeleteUserException;
import io.vavr.control.Either;

public interface DeleteUserUseCase {
    Either<DeleteUserException, Void> delete(String id);
}
