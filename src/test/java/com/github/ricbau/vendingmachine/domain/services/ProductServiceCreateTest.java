package com.github.ricbau.vendingmachine.domain.services;

import com.github.ricbau.vendingmachine.domain.commands.CreateProductCommand;
import com.github.ricbau.vendingmachine.domain.commands.CreateProductCommand.WriteProductPayload;
import com.github.ricbau.vendingmachine.domain.entities.Product;
import com.github.ricbau.vendingmachine.domain.exceptions.CreateProductException;
import com.github.ricbau.vendingmachine.domain.mappers.ProductCommandMapperImpl;
import com.github.ricbau.vendingmachine.domain.ports.ProductCrudPort;
import io.vavr.control.Try;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@DisplayName("When a product is being created")
@ExtendWith(MockitoExtension.class)
class ProductServiceCreateTest {
    @Mock
    private ProductCrudPort productCrudPort;
    @Spy
    private ProductCommandMapperImpl productCommandMapper;
    @InjectMocks
    private ProductService productService;
    @Captor
    private ArgumentCaptor<Product> productArgumentCaptor;

    @Test
    @DisplayName("the input command should be mapped and persisted")
    void create() {
        //Given
        when(productCrudPort.persist(any()))
                .thenReturn(Try.of(() -> new Product(
                        "333", "test-product",
                        1, 2, Arrays.asList("seller1", "seller2"),
                        "user"
                )));

        //When //Then
        assertThat(productService.create(
                new CreateProductCommand(
                        new WriteProductPayload(
                                "test-product",
                                1, 2, Arrays.asList("seller1", "seller2")
                        ),
                        "user"
                )
        ).get()).hasNoNullFieldsOrProperties();

        verify(productCrudPort).persist(productArgumentCaptor.capture());
        assertThat(productArgumentCaptor.getValue())
                .hasFieldOrPropertyWithValue("productName", "test-product")
                .hasFieldOrPropertyWithValue("amountAvailable", 1)
                .hasFieldOrPropertyWithValue("costInCents", 2)
                .hasFieldOrPropertyWithValue("sellerIds", Arrays.asList("seller1", "seller2"));

    }

    @Test
    @DisplayName("it should return an exception in case of failure")
    void returnException() {
        //Given
        doThrow(RuntimeException.class).when(productCrudPort).persist(any());

        //When //Then
        assertThat(productService.create(
                new CreateProductCommand(
                        new WriteProductPayload(
                                "test-product",
                                1, 2, Arrays.asList("seller1", "seller2")),
                        "user"
                )
        ).getLeft())
                .isInstanceOf(CreateProductException.class)
                .hasCauseInstanceOf(RuntimeException.class);
    }
}