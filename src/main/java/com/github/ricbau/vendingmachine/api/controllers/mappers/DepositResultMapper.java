package com.github.ricbau.vendingmachine.api.controllers.mappers;

import com.github.ricbau.vendingmachine.api.controllers.results.CreateDepositResult;
import com.github.ricbau.vendingmachine.domain.entities.Deposit;
import com.github.ricbau.vendingmachine.domain.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@SuppressWarnings("unused")
@Mapper(componentModel = "spring")
public interface DepositResultMapper {
    @Mapping(target = "userLocation", source = "affectedUser")
    CreateDepositResult toResult(Deposit deposit);

    default URI toURI(User user) {
        return ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/users/{userId}")
                .buildAndExpand(user.getId())
                .toUri();
    }
}
