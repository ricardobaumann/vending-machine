package com.github.ricbau.vendingmachine.domain.exceptions;

public class CreateDepositException extends RuntimeException {
    public CreateDepositException(String message, Throwable cause) {
        super(message, cause);
    }

    public CreateDepositException(Throwable cause) {
        super(cause);
    }

}
