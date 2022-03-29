package com.github.ricbau.vendingmachine.domain.services;

import com.github.ricbau.vendingmachine.domain.commands.CreateUserCommand;
import com.github.ricbau.vendingmachine.domain.entities.User;
import com.github.ricbau.vendingmachine.domain.exceptions.CreateUserException;
import com.github.ricbau.vendingmachine.domain.exceptions.DeleteUserException;
import com.github.ricbau.vendingmachine.domain.exceptions.DuplicateUserException;
import com.github.ricbau.vendingmachine.domain.mappers.UserCommandMapper;
import com.github.ricbau.vendingmachine.domain.ports.UserCrudPort;
import com.github.ricbau.vendingmachine.domain.usecases.CreateUserUseCase;
import com.github.ricbau.vendingmachine.domain.usecases.DeleteUserUseCase;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PersistUserService implements CreateUserUseCase, DeleteUserUseCase {

    private final UserCrudPort userCrudPort;
    private final UserCommandMapper userCommandMapper;

    @Override
    public Either<CreateUserException, User> create(CreateUserCommand createUserCommand) {
        return Try.of(() -> userCommandMapper.toUser(createUserCommand))
                .andThenTry(this::checkForDuplicate)
                .flatMap(userCrudPort::persist)
                .toEither()
                .mapLeft(CreateUserException::new);
    }

    private void checkForDuplicate(User user) {
        if (userCrudPort.existsByUsername(user.getUsername())) {
            throw new DuplicateUserException(user.getUsername());
        }
    }

    @Override
    public Either<DeleteUserException, Void> delete(String id) {
        return userCrudPort.delete(id)
                .toEither()
                .mapLeft(DeleteUserException::new);
    }

    public Try<User> addToBalanceOf(User user, Integer amount) {
        return userCrudPort.persist(
                new User(
                        user.getId(),
                        user.getUsername(),
                        user.getPassword(),
                        user.getBalanceInCents() + amount,
                        user.getRoles()
                )
        );
    }
}
