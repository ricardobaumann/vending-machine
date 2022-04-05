package com.github.ricbau.vendingmachine.domain.commands;

import com.github.ricbau.vendingmachine.domain.entities.Product;
import lombok.Value;

@Value
public class DecreaseAmountCommand {
    Product product;
    Integer amount;
}
