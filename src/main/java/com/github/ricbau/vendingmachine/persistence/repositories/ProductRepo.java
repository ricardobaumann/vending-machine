package com.github.ricbau.vendingmachine.persistence.repositories;

import com.github.ricbau.vendingmachine.persistence.entities.ProductEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends CrudRepository<ProductEntity, String> {
}
