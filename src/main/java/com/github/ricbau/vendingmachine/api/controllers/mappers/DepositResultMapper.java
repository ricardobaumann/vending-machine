package com.github.ricbau.vendingmachine.api.controllers.mappers;

import com.github.ricbau.vendingmachine.api.controllers.results.CreateDepositResult;
import com.github.ricbau.vendingmachine.domain.entities.Deposit;
import org.mapstruct.Mapper;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@SuppressWarnings("unused")
@Mapper(componentModel = "spring")
public interface DepositResultMapper {
    CreateDepositResult toResult(Deposit deposit);

    default URI toURI(String userId) {
        return ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/users/{userId}")
                .buildAndExpand(userId)
                .toUri();
    }
}
