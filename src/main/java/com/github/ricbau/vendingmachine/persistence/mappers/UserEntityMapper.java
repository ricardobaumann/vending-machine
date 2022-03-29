package com.github.ricbau.vendingmachine.persistence.mappers;

import com.github.ricbau.vendingmachine.domain.entities.User;
import com.github.ricbau.vendingmachine.persistence.entities.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserEntityMapper {
    UserEntity toEntity(User user);
}
