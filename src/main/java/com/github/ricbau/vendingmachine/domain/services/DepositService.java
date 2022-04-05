package com.github.ricbau.vendingmachine.domain.services;

import com.github.ricbau.vendingmachine.domain.commands.CreateDepositCommand;
import com.github.ricbau.vendingmachine.domain.entities.Deposit;
import com.github.ricbau.vendingmachine.domain.exceptions.CreateDepositException;
import com.github.ricbau.vendingmachine.domain.exceptions.UserNotFoundException;
import com.github.ricbau.vendingmachine.domain.ports.UserCrudPort;
import com.github.ricbau.vendingmachine.domain.usecases.CreateDepositUseCase;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DepositService implements CreateDepositUseCase {

    private final UserCrudPort userCrudPort;
    private final UserService userService;

    @Override
    public Either<CreateDepositException, Deposit> create(CreateDepositCommand createDepositCommand) {
        String userId = createDepositCommand.getUserId();
        return Try.of(() -> userCrudPort.findByID(userId)
                        .orElseThrow(() -> new UserNotFoundException(userId)))
                .flatMap(user -> userService.addToBalanceOf(user, createDepositCommand.getAmount()))
                .map(Deposit::new)
                .toEither()
                .mapLeft(CreateDepositException::new);
    }
}
