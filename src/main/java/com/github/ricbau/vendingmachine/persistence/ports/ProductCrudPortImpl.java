package com.github.ricbau.vendingmachine.persistence.ports;

import com.github.ricbau.vendingmachine.domain.entities.Product;
import com.github.ricbau.vendingmachine.domain.ports.ProductCrudPort;
import com.github.ricbau.vendingmachine.persistence.mappers.ProductEntityMapper;
import com.github.ricbau.vendingmachine.persistence.repositories.ProductRepo;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Repository
@AllArgsConstructor
public class ProductCrudPortImpl implements ProductCrudPort {

    private final ProductRepo productRepo;
    private final ProductEntityMapper productEntityMapper;

    @Override
    public Try<Product> persist(Product product) {
        return Try.of(() -> {
            productRepo.save(productEntityMapper.toEntity(product));
            return product;
        });
    }

    @Override
    public Optional<Product> findByID(String productId) {
        return productRepo.findById(productId)
                .map(productEntityMapper::toProduct);
    }

    @Override
    public Try<Void> delete(String id) {
        return Try.run(() -> productRepo.deleteById(id));
    }

    @Override
    public Optional<Product> findById(String id) {
        return productRepo.findById(id)
                .map(productEntityMapper::toProduct);
    }

    @Override
    public List<Product> getAll() {
        return StreamSupport.stream(productRepo.findAll().spliterator(), false)
                .map(productEntityMapper::toProduct)
                .collect(Collectors.toList());
    }
}
