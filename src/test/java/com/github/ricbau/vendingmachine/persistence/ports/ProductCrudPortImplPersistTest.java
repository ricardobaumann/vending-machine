package com.github.ricbau.vendingmachine.persistence.ports;

import com.github.ricbau.vendingmachine.domain.entities.Product;
import com.github.ricbau.vendingmachine.persistence.mappers.ProductEntityMapperImpl;
import com.github.ricbau.vendingmachine.persistence.repositories.ProductRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("When a product is being persisted")
class ProductCrudPortImplPersistTest {

    @Autowired
    private ProductRepo productRepo;

    private ProductCrudPortImpl productCrudPort;

    @BeforeEach
    void setUp() {
        productCrudPort = new ProductCrudPortImpl(
                productRepo,
                new ProductEntityMapperImpl());
        productRepo.deleteAll();
    }

    @Test
    @DisplayName("it should be mapped and persisted in the database")
    void persist() {

        //Given
        Product product = new Product(
                "100", "test-product",
                1, 1, Arrays.asList("seller1", "seller2")
        );
        productCrudPort.persist(
                product
        ).get();

        //When //Then
        assertThat(productRepo.findById("100"))
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(product);
    }
}