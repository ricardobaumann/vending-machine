package com.github.ricbau.vendingmachine.domain.services;

import com.github.ricbau.vendingmachine.domain.commands.CreateSalesCommand;
import com.github.ricbau.vendingmachine.domain.commands.DecreaseAmountCommand;
import com.github.ricbau.vendingmachine.domain.entities.Sale;
import com.github.ricbau.vendingmachine.domain.exceptions.CreateSaleException;
import com.github.ricbau.vendingmachine.domain.mappers.SaleCommandMapper;
import com.github.ricbau.vendingmachine.domain.usecases.CreateSaleUseCase;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class SaleService implements CreateSaleUseCase {

    private final SaleCommandMapper saleCommandMapper;
    private final ProductService productService;

    @Override
    public Either<CreateSaleException, Sale> create(CreateSalesCommand createSalesCommand) {
        return Try.of(() -> saleCommandMapper.toSale(createSalesCommand))
                .andThenTry(this::updateProduct)
                .andThenTry(this::persist)
                .toEither()
                .mapLeft(CreateSaleException::new);
    }

    private void updateProduct(Sale sale) {
        productService.decreaseAmount(
                new DecreaseAmountCommand(sale.getProduct(), sale.getAmount()));
    }

    private void persist(Sale sale) {
        //TODO persist
    }
}
