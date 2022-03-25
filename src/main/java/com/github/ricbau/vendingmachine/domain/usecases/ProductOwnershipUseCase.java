package com.github.ricbau.vendingmachine.domain.usecases;

public interface ProductOwnershipUseCase {
    boolean isAllowedToWrite(String username, String productId);
}
