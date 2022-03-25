package com.github.ricbau.vendingmachine.api.controllers.mappers;

import com.github.ricbau.vendingmachine.api.controllers.results.WriteResult;
import com.github.ricbau.vendingmachine.domain.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@SuppressWarnings("unused")
@Mapper(componentModel = "spring")
public interface CreateResultMapper {

    @Mapping(target = "location", source = "id")
    WriteResult toResult(Product product);

    default URI toURI(String id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }

}
