package com.github.ricbau.vendingmachine.api.controllers.mappers;

import com.github.ricbau.vendingmachine.api.controllers.results.ProductResult;
import com.github.ricbau.vendingmachine.domain.entities.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductResultMapper {
    ProductResult toResult(Product product);
}
