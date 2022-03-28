package com.github.ricbau.vendingmachine.domain.exceptions;

public class DeleteProductException extends RuntimeException {
    public DeleteProductException(String message, Throwable cause) {
        super(message, cause);
    }

    public DeleteProductException(Throwable cause) {
        super(cause);
    }
}
