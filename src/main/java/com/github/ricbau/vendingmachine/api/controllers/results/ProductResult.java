package com.github.ricbau.vendingmachine.api.controllers.results;

import java.net.URI;
import java.util.List;

public record ProductResult(
        String id,
        String productName,
        Integer amountAvailable,
        Integer costInCents,
        List<String> sellerIds,
        URI location
) {
}
