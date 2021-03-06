package com.github.ricbau.vendingmachine.domain.commands;

import lombok.Value;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Value
public class CreateProductCommand {
    WriteProductPayload payload;
    String username;

    @Value
    public static class WriteProductPayload {
        @NotBlank String productName;
        @NotNull @Min(0) Integer amountAvailable;
        @NotNull @Min(1) Integer costInCents;
        @NotNull @Size(min = 1) List<String> sellerIds;
    }
}
