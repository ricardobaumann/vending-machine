package com.github.ricbau.vendingmachine.domain.ports;

import com.github.ricbau.vendingmachine.domain.entities.User;
import io.vavr.control.Try;

public interface UserCrudPort {
    Try<Void> persist(User user);
}
