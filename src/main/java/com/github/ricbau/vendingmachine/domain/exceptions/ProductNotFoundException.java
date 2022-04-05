package com.github.ricbau.vendingmachine.domain.exceptions;

import javax.validation.constraints.NotBlank;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(@NotBlank String productId) {
        super(productId);
    }
}
