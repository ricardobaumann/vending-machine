package com.github.ricbau.vendingmachine.domain.ports;

import com.github.ricbau.vendingmachine.domain.entities.Sale;
import io.vavr.control.Try;

public interface SaleCrudPort {
    Try<Sale> persist(Sale sale);
}
