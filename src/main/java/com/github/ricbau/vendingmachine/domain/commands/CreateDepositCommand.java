package com.github.ricbau.vendingmachine.domain.commands;

import lombok.Value;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Value
public class CreateDepositCommand {
    @NotBlank String userId;
    @NotNull @Min(5) Integer amount;
}
