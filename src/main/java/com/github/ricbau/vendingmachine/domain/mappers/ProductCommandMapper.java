package com.github.ricbau.vendingmachine.domain.mappers;

import com.github.ricbau.vendingmachine.domain.commands.CreateProductCommand;
import com.github.ricbau.vendingmachine.domain.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductCommandMapper {
    @Mapping(target = "id", expression = "java(java.util.UUID.randomUUID().toString())")
    @Mapping(target = "owner", source = "username")
    @Mapping(target = ".", source = "payload")
    Product toProduct(CreateProductCommand createProductCommand);
}
