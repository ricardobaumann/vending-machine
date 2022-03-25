package com.github.ricbau.vendingmachine.domain.ports;

import com.github.ricbau.vendingmachine.domain.entities.Product;

public interface ProductCrudPort {
    void persist(Product product);
}
