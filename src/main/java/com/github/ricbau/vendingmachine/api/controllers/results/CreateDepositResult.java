package com.github.ricbau.vendingmachine.api.controllers.results;

import lombok.Value;

import java.net.URI;

@Value
public class CreateDepositResult {
    URI userLocation;
}
