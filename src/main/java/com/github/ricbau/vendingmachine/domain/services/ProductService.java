package com.github.ricbau.vendingmachine.domain.services;

import com.github.ricbau.vendingmachine.domain.commands.CreateProductCommand;
import com.github.ricbau.vendingmachine.domain.commands.DecreaseAmountCommand;
import com.github.ricbau.vendingmachine.domain.commands.UpdateProductCommand;
import com.github.ricbau.vendingmachine.domain.entities.Product;
import com.github.ricbau.vendingmachine.domain.exceptions.CreateProductException;
import com.github.ricbau.vendingmachine.domain.exceptions.DeleteProductException;
import com.github.ricbau.vendingmachine.domain.exceptions.ProductUnavailableException;
import com.github.ricbau.vendingmachine.domain.exceptions.UpdateProductException;
import com.github.ricbau.vendingmachine.domain.mappers.ProductCommandMapper;
import com.github.ricbau.vendingmachine.domain.ports.ProductCrudPort;
import com.github.ricbau.vendingmachine.domain.usecases.CreateProductUseCase;
import com.github.ricbau.vendingmachine.domain.usecases.DeleteProductUseCase;
import com.github.ricbau.vendingmachine.domain.usecases.UpdateProductUseCase;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductService implements CreateProductUseCase,
        UpdateProductUseCase, DeleteProductUseCase {

    private final ProductCrudPort productCrudPort;
    private final ProductCommandMapper productCommandMapper;

    @Override
    public Either<CreateProductException, Product> create(CreateProductCommand createProductCommand) {
        return Try.of(() -> productCommandMapper.toProduct(createProductCommand))
                .flatMap(productCrudPort::persist)
                .toEither()
                .mapLeft(CreateProductException::new);
    }

    @Override
    public Either<UpdateProductException, Product> update(UpdateProductCommand updateProductCommand) {
        return Try.of(() -> productCommandMapper.toProduct(updateProductCommand))
                .flatMap(productCrudPort::persist)
                .toEither()
                .mapLeft(UpdateProductException::new);
    }

    @Override
    public Either<DeleteProductException, Void> delete(String id) {
        return productCrudPort.delete(id)
                .toEither()
                .mapLeft(DeleteProductException::new);
    }

    Try<Product> decreaseAmount(DecreaseAmountCommand decreaseAmountCommand) {

        Product product = decreaseAmountCommand.getProduct();
        return Try.run(() -> {
            if (product.getAmountAvailable() < decreaseAmountCommand.getAmount()) {
                throw new ProductUnavailableException(product.getProductName());
            }
        }).flatMap(__ -> productCrudPort.persist(
                new Product(
                        product.getId(),
                        product.getProductName(),
                        product.getAmountAvailable() - decreaseAmountCommand.getAmount(),
                        product.getCostInCents(),
                        product.getSellerIds(),
                        product.getOwner()
                )));
    }
}
