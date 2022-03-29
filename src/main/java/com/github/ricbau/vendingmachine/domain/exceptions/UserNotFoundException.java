package com.github.ricbau.vendingmachine.domain.exceptions;

import javax.validation.constraints.NotBlank;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(@NotBlank String userId) {
        super(userId);
    }
}
