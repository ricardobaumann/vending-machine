package com.github.ricbau.vendingmachine.domain.services;

import com.github.ricbau.vendingmachine.domain.commands.CreateProductCommand;
import com.github.ricbau.vendingmachine.domain.entities.Product;
import com.github.ricbau.vendingmachine.domain.exceptions.CreateProductException;
import com.github.ricbau.vendingmachine.domain.mappers.ProductCommandMapper;
import com.github.ricbau.vendingmachine.domain.ports.ProductCrudPort;
import com.github.ricbau.vendingmachine.domain.usecases.CreateProductUseCase;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreateProductService implements CreateProductUseCase {

    private final ProductCrudPort productCrudPort;
    private final ProductCommandMapper productCommandMapper;

    @Override
    public Either<CreateProductException, Product> create(CreateProductCommand createProductCommand) {
        return Try.of(() -> productCommandMapper.toProduct(createProductCommand))
                .andThenTry(productCrudPort::persist)
                .toEither()
                .mapLeft(CreateProductException::new);
    }
}
