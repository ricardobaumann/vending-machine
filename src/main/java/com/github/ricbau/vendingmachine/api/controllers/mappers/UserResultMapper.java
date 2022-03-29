package com.github.ricbau.vendingmachine.api.controllers.mappers;

import com.github.ricbau.vendingmachine.api.controllers.results.UserResult;
import com.github.ricbau.vendingmachine.domain.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserResultMapper {
    UserResult toResult(User user);
}
