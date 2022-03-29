package com.github.ricbau.vendingmachine.persistence.ports;

import com.github.ricbau.vendingmachine.domain.entities.User;
import com.github.ricbau.vendingmachine.domain.ports.UserCrudPort;
import com.github.ricbau.vendingmachine.persistence.mappers.UserEntityMapper;
import com.github.ricbau.vendingmachine.persistence.repositories.UserRepo;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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

    @Override
    public boolean existsByUsername(String username) {
        return userRepo.existsByUsername(username);
    }

    @Override
    public Try<Void> delete(String id) {
        return Try.run(() -> userRepo.deleteById(id));
    }

    @Override
    public Optional<User> findByID(String id) {
        return userRepo.findById(id)
                .map(userEntityMapper::toUser);
    }

    @Override
    public List<User> getAll() {
        return StreamSupport.stream(userRepo.findAll().spliterator(), false)
                .map(userEntityMapper::toUser)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepo.findByUsername(username)
                .map(userEntityMapper::toUser);
    }

}
