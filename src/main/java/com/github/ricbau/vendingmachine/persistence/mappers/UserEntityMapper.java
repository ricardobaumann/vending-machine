package com.github.ricbau.vendingmachine.persistence.mappers;

import com.github.ricbau.vendingmachine.domain.entities.Password;
import com.github.ricbau.vendingmachine.domain.entities.User;
import com.github.ricbau.vendingmachine.persistence.entities.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserEntityMapper {
    UserEntity toEntity(User user);

    User toUser(UserEntity userEntity);

    default String map(Password password) {
        return password.getPassword();
    }

    default Password map(String password) {
        return new Password(password);
    }
}
