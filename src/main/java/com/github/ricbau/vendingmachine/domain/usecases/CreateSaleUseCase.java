package com.github.ricbau.vendingmachine.domain.usecases;

import com.github.ricbau.vendingmachine.domain.commands.CreateSalesCommand;
import com.github.ricbau.vendingmachine.domain.entities.Sale;
import com.github.ricbau.vendingmachine.domain.exceptions.CreateSaleException;
import io.vavr.control.Either;

public interface CreateSaleUseCase {
    Either<CreateSaleException, Sale> create(CreateSalesCommand createSalesCommand);
}
