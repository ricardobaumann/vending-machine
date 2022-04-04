package com.github.ricbau.vendingmachine.domain.commands;

import lombok.Value;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Value
public class CreateSalesCommand {
    @NotBlank String productId;
    @NotNull @Min(1) Integer amount;
    @NotBlank String userId;
}
