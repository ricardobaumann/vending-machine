package com.github.ricbau.vendingmachine.domain.exceptions;

public class ProductUnavailableException extends RuntimeException {
    public ProductUnavailableException(String productName) {
        super(productName);
    }
}
