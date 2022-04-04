package com.github.ricbau.vendingmachine.api.controllers.mappers;

import com.github.ricbau.vendingmachine.api.controllers.results.WriteResult;
import com.github.ricbau.vendingmachine.domain.entities.Sale;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Mapper(componentModel = "spring")
public interface SalesResultMapper {
    @Mapping(target = "location", source = "id")
    WriteResult toResult(Sale sale);

    default URI map(String id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .buildAndExpand(id)
                .toUri();
    }
}
