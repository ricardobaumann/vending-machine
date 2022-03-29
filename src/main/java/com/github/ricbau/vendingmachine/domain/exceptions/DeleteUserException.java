package com.github.ricbau.vendingmachine.domain.exceptions;

public class DeleteUserException extends RuntimeException {
    public DeleteUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public DeleteUserException(Throwable cause) {
        super(cause);
    }
}
