package com.github.ricbau.vendingmachine.persistence.ports;

import com.github.ricbau.vendingmachine.domain.entities.Product;
import com.github.ricbau.vendingmachine.domain.ports.ProductCrudPort;
import com.github.ricbau.vendingmachine.persistence.mappers.ProductEntityMapper;
import com.github.ricbau.vendingmachine.persistence.repositories.ProductRepo;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class ProductCrudPortImpl implements ProductCrudPort {

    private final ProductRepo productRepo;
    private final ProductEntityMapper productEntityMapper;

    @Override
    public Try<Void> persist(Product product) {
        return Try.run(() -> productRepo.save(productEntityMapper.toEntity(product)));
    }

    @Override
    public Optional<Product> findByID(String productId) {
        return productRepo.findById(productId)
                .map(productEntityMapper::toProduct);
    }
}
