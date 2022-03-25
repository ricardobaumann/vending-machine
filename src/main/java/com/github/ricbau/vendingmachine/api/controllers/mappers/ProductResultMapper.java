package com.github.ricbau.vendingmachine.api.controllers.mappers;

import com.github.ricbau.vendingmachine.api.controllers.results.CreateProductResult;
import com.github.ricbau.vendingmachine.domain.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@SuppressWarnings("unused")
@Mapper(componentModel = "spring")
public interface ProductResultMapper {

    @Mapping(target = "location", source = "id")
    CreateProductResult toResult(Product product);

    default URI toURI(String id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }

}
