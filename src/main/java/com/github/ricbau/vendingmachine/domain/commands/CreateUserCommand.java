package com.github.ricbau.vendingmachine.domain.commands;

import com.github.ricbau.vendingmachine.persistence.entities.UserRole;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Value
public class CreateUserCommand {
    @NotBlank String username;
    @NotBlank String password;
    @NotNull @Size(min = 1) Set<UserRole> roles;
}
