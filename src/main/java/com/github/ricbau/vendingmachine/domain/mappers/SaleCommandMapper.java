package com.github.ricbau.vendingmachine.domain.mappers;

import com.github.ricbau.vendingmachine.domain.commands.CreateSalesCommand;
import com.github.ricbau.vendingmachine.domain.entities.Sale;
import com.github.ricbau.vendingmachine.domain.exceptions.ProductNotFoundException;
import com.github.ricbau.vendingmachine.domain.exceptions.UserNotFoundException;
import com.github.ricbau.vendingmachine.domain.usecases.ReadProductUseCase;
import com.github.ricbau.vendingmachine.domain.usecases.ReadUserUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class SaleCommandMapper {

    private final ReadProductUseCase readProductUseCase;
    private final ReadUserUseCase readUserUseCase;

    public Sale toSale(CreateSalesCommand createSalesCommand) {
        return new Sale(
                UUID.randomUUID().toString(),
                readProductUseCase.findById(createSalesCommand.getProductId())
                        .orElseThrow(() -> new ProductNotFoundException(createSalesCommand.getProductId())),
                createSalesCommand.getAmount(),
                readUserUseCase.findByID(createSalesCommand.getUserId())
                        .orElseThrow(() -> new UserNotFoundException(createSalesCommand.getUserId()))
        );
    }
}
