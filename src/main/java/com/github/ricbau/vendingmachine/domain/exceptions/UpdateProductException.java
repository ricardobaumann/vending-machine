package com.github.ricbau.vendingmachine.domain.exceptions;

public class UpdateProductException extends RuntimeException {
    public UpdateProductException(String message, Throwable cause) {
        super(message, cause);
    }

    public UpdateProductException(Throwable cause) {
        super(cause);
    }
}
