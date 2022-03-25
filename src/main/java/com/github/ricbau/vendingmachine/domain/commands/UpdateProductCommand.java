package com.github.ricbau.vendingmachine.domain.commands;

import lombok.Value;

@Value
public class UpdateProductCommand {
    CreateProductCommand createProductCommand;
    String id;
}
