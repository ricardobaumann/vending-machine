package com.github.ricbau.vendingmachine.persistence.ports;

import com.github.ricbau.vendingmachine.domain.entities.User;
import com.github.ricbau.vendingmachine.domain.ports.UserCrudPort;
import com.github.ricbau.vendingmachine.persistence.mappers.UserEntityMapper;
import com.github.ricbau.vendingmachine.persistence.repositories.UserRepo;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@AllArgsConstructor
public class UserCrudPortImpl implements UserCrudPort {

    private final UserRepo userRepo;
    private final UserEntityMapper userEntityMapper;

    @Override
    public Try<User> persist(User user) {
        return Try.of(() -> {
            userRepo.save(userEntityMapper.toEntity(user));
            log.info("User {} persisted successfully", user);
            return user;
        });
    }
}
