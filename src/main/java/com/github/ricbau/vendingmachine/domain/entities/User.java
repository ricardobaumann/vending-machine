package com.github.ricbau.vendingmachine.domain.entities;

import com.github.ricbau.vendingmachine.persistence.entities.UserRole;
import lombok.Value;

import java.util.Set;

@Value
public class User {
    String id;
    String username;
    String password;
    Integer balanceInCents;
    Set<UserRole> roles;
}
