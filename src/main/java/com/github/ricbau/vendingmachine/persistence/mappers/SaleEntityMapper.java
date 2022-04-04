package com.github.ricbau.vendingmachine.persistence.mappers;

import com.github.ricbau.vendingmachine.domain.entities.Product;
import com.github.ricbau.vendingmachine.domain.entities.Sale;
import com.github.ricbau.vendingmachine.persistence.entities.ProductEntity;
import com.github.ricbau.vendingmachine.persistence.entities.SaleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SaleEntityMapper {

    @Mapping(target = "username", source = "user.username")
    SaleEntity toEntity(Sale sale);

    default ProductEntity map(Product product) {
        return ProductEntity.builder()
                .id(product.getId())
                .build();
    }
}
