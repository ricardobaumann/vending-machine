package com.github.ricbau.vendingmachine.domain.exceptions;

public class CreateUserException extends RuntimeException {
    public CreateUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public CreateUserException(Throwable cause) {
        super(cause);
    }
}
