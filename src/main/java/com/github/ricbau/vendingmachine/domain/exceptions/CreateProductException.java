package com.github.ricbau.vendingmachine.domain.exceptions;

public class CreateProductException extends RuntimeException {
    public CreateProductException(Throwable throwable) {
        super(throwable);
    }
}
