package com.github.ricbau.vendingmachine.persistence.repositories;

import com.github.ricbau.vendingmachine.persistence.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends CrudRepository<UserEntity, String> {
}
