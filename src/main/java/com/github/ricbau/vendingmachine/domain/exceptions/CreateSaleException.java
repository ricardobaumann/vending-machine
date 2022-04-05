package com.github.ricbau.vendingmachine.domain.exceptions;

public class CreateSaleException extends RuntimeException {
    public CreateSaleException(String message, Throwable cause) {
        super(message, cause);
    }

    public CreateSaleException(Throwable cause) {
        super(cause);
    }
}
