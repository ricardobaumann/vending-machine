package com.github.ricbau.vendingmachine.domain.entities;

import lombok.Value;

@Value
public class Sale {
    String id;
    Product product;
    Integer amount;
    User user;
}
