package com.github.ricbau.vendingmachine.domain.ports;

import com.github.ricbau.vendingmachine.domain.entities.User;
import com.github.ricbau.vendingmachine.domain.usecases.ReadUserUseCase;
import io.vavr.control.Try;

public interface UserCrudPort extends ReadUserUseCase {
    Try<User> persist(User user);

    boolean existsByUsername(String username);

    Try<Void> delete(String id);
}
