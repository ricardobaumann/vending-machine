package com.github.ricbau.vendingmachine.persistence.ports;

import com.github.ricbau.vendingmachine.domain.entities.Sale;
import com.github.ricbau.vendingmachine.domain.ports.SaleCrudPort;
import com.github.ricbau.vendingmachine.persistence.mappers.SaleEntityMapper;
import com.github.ricbau.vendingmachine.persistence.repositories.SaleRepo;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class SalePortCrudImpl implements SaleCrudPort {

    private final SaleRepo saleRepo;
    private final SaleEntityMapper saleEntityMapper;

    @Override
    public Try<Sale> persist(Sale sale) {
        return Try.of(() -> {
            saleRepo.save(saleEntityMapper.toEntity(sale));
            return sale;
        });
    }
}
