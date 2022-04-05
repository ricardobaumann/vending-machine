package com.github.ricbau.vendingmachine.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ProductUnavailableException extends RuntimeException {
    public ProductUnavailableException(String productName) {
        super(productName);
    }
}
