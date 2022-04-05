package com.github.ricbau.vendingmachine.persistence.repositories;

import com.github.ricbau.vendingmachine.persistence.entities.SaleEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleRepo extends CrudRepository<SaleEntity, String> {
}
