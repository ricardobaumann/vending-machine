package com.github.ricbau.vendingmachine.domain.services;

import com.github.ricbau.vendingmachine.domain.entities.Product;
import com.github.ricbau.vendingmachine.domain.ports.ProductCrudPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductOwnershipServiceTest {

    @Mock
    private ProductCrudPort mockProductCrudPort;

    private ProductOwnershipService productOwnershipServiceUnderTest;

    @BeforeEach
    void setUp() {
        productOwnershipServiceUnderTest = new ProductOwnershipService(mockProductCrudPort);
    }

    @Test
    void testIsAllowedToWrite() {
        // Setup
        // Configure ProductCrudPort.findByID(...).
        final Optional<Product> product = Optional.of(
                new Product("id", "productName", 0, 0, List.of("value"), "owner"));
        when(mockProductCrudPort.findByID("productId")).thenReturn(product);

        // Run the test
        final boolean result = productOwnershipServiceUnderTest.isAllowedToWrite("owner", "productId");

        // Verify the results
        assertThat(result).isTrue();
    }

    @Test
    void testIsAllowedToWrite_ProductCrudPortReturnsAbsent() {
        // Setup
        when(mockProductCrudPort.findByID("productId")).thenReturn(Optional.empty());

        // Run the test
        final boolean result = productOwnershipServiceUnderTest.isAllowedToWrite("username", "productId");

        // Verify the results
        assertThat(result).isFalse();
    }
}
