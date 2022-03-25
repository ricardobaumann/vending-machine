package com.github.ricbau.vendingmachine.persistence.mappers;

import com.github.ricbau.vendingmachine.domain.entities.Product;
import com.github.ricbau.vendingmachine.persistence.entities.ProductEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductEntityMapper {
    ProductEntity toEntity(Product product);

    Product toProduct(ProductEntity productEntity);
}
