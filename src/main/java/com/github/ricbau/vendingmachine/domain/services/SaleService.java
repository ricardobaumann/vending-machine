package com.github.ricbau.vendingmachine.domain.services;

import com.github.ricbau.vendingmachine.domain.commands.CreateSalesCommand;
import com.github.ricbau.vendingmachine.domain.commands.DecreaseAmountCommand;
import com.github.ricbau.vendingmachine.domain.entities.Sale;
import com.github.ricbau.vendingmachine.domain.exceptions.CreateSaleException;
import com.github.ricbau.vendingmachine.domain.mappers.SaleCommandMapper;
import com.github.ricbau.vendingmachine.domain.ports.SaleCrudPort;
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
    private final SaleCrudPort saleCrudPort;

    @Override
    public Either<CreateSaleException, Sale> create(CreateSalesCommand createSalesCommand) {
        return Try.of(() -> saleCommandMapper.toSale(createSalesCommand))
                .flatMap(this::updateProduct)
                .flatMap(this::persist)
                .toEither()
                .mapLeft(CreateSaleException::new);
    }

    private Try<Sale> updateProduct(Sale sale) {
        return productService.decreaseAmount(
                        new DecreaseAmountCommand(sale.getProduct(), sale.getAmount()))
                .map(product -> sale);
    }

    private Try<Sale> persist(Sale sale) {
        return saleCrudPort.persist(sale);
    }
}
