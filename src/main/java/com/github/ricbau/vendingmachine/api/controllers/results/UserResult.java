package com.github.ricbau.vendingmachine.api.controllers.results;

import com.github.ricbau.vendingmachine.persistence.entities.UserRole;
import lombok.Value;

import java.util.Set;

@Value
public class UserResult {
    String id;
    String username;
    Integer balanceInCents;
    Set<UserRole> roles;
}
