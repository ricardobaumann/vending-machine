package com.github.ricbau.vendingmachine.domain.services;

import com.github.ricbau.vendingmachine.domain.commands.CreateUserCommand;
import com.github.ricbau.vendingmachine.domain.entities.User;
import com.github.ricbau.vendingmachine.domain.exceptions.CreateUserException;
import com.github.ricbau.vendingmachine.domain.mappers.UserCommandMapper;
import com.github.ricbau.vendingmachine.domain.ports.UserCrudPort;
import com.github.ricbau.vendingmachine.domain.usecases.CreateUserUseCase;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PersistUserService implements CreateUserUseCase {

    private final UserCrudPort userCrudPort;
    private final UserCommandMapper userCommandMapper;

    @Override
    public Either<CreateUserException, User> create(CreateUserCommand createUserCommand) {
        return Try.of(() -> userCommandMapper.toUser(createUserCommand))
                .flatMap(userCrudPort::persist)
                .toEither()
                .mapLeft(CreateUserException::new);

    }
}
