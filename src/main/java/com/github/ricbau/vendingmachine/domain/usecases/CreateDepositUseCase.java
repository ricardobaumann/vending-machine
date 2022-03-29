package com.github.ricbau.vendingmachine.domain.usecases;

import com.github.ricbau.vendingmachine.domain.commands.CreateDepositCommand;
import com.github.ricbau.vendingmachine.domain.entities.Deposit;
import com.github.ricbau.vendingmachine.domain.exceptions.CreateDepositException;
import io.vavr.control.Either;

public interface CreateDepositUseCase {
    Either<CreateDepositException, Deposit> create(CreateDepositCommand createDepositCommand);
}
