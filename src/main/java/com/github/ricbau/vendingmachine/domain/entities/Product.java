package com.github.ricbau.vendingmachine.domain.entities;

import lombok.Value;

import java.util.List;

@Value
public class Product {
    String id;
    String productName;
    Integer amountAvailable;
    Integer costInCents;
    List<String> sellerIds;
    String owner;
}