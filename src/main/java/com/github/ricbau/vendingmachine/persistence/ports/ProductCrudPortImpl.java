package com.github.ricbau.vendingmachine.persistence.ports;

import com.github.ricbau.vendingmachine.domain.entities.Product;
import com.github.ricbau.vendingmachine.domain.ports.ProductCrudPort;
import com.github.ricbau.vendingmachine.persistence.mappers.ProductEntityMapper;
import com.github.ricbau.vendingmachine.persistence.repositories.ProductRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class ProductCrudPortImpl implements ProductCrudPort {

    private final ProductRepo productRepo;
    private final ProductEntityMapper productEntityMapper;

    @Override
    public void persist(Product product) {
        productRepo.save(productEntityMapper.toEntity(product));
    }
}
