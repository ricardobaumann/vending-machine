package com.github.ricbau.vendingmachine.domain.usecases;

import com.github.ricbau.vendingmachine.domain.entities.User;

import java.util.List;
import java.util.Optional;

public interface ReadUserUseCase {
    Optional<User> findByID(String id);

    List<User> getAll();

    Optional<User> findByUsername(String username);
}
