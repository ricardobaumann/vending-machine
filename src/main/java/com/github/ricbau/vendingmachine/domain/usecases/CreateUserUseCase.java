package com.github.ricbau.vendingmachine.domain.usecases;

import com.github.ricbau.vendingmachine.domain.commands.CreateUserCommand;
import com.github.ricbau.vendingmachine.domain.entities.User;
import com.github.ricbau.vendingmachine.domain.exceptions.CreateUserException;
import io.vavr.control.Either;

public interface CreateUserUseCase {
    Either<CreateUserException, User> create(CreateUserCommand createUserCommand);
    
}
