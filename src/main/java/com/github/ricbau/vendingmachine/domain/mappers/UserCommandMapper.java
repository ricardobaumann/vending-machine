package com.github.ricbau.vendingmachine.domain.mappers;

import com.github.ricbau.vendingmachine.domain.commands.CreateUserCommand;
import com.github.ricbau.vendingmachine.domain.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = PasswordMapper.class)
public interface UserCommandMapper {
    @Mapping(target = "id", expression = "java(java.util.UUID.randomUUID().toString())")
    @Mapping(target = "balanceInCents", constant = "0")
    User toUser(CreateUserCommand createUserCommand);
}
