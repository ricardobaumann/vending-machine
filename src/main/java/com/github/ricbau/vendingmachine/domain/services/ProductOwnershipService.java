package com.github.ricbau.vendingmachine.domain.services;

import com.github.ricbau.vendingmachine.domain.entities.Product;
import com.github.ricbau.vendingmachine.domain.ports.ProductCrudPort;
import com.github.ricbau.vendingmachine.domain.usecases.ProductOwnershipUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductOwnershipService implements ProductOwnershipUseCase {

    private final ProductCrudPort productCrudPort;

    @Override
    public boolean isAllowedToWrite(String username, String productId) {
        return productCrudPort.findByID(productId)
                .map(Product::getOwner)
                .filter(username::equals)
                .isPresent();
    }
}
