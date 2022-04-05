package com.github.ricbau.vendingmachine.domain.services;

import com.github.ricbau.vendingmachine.domain.commands.CreateProductCommand;
import com.github.ricbau.vendingmachine.domain.commands.UpdateProductCommand;
import com.github.ricbau.vendingmachine.domain.entities.Product;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("When a product is being updated")
@ExtendWith(MockitoExtension.class)
class ProductServiceUpdateTest {

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
    void update() {
        //Given
        when(productCrudPort.persist(any()))
                .thenReturn(Try.of(() -> new Product(
                        "333", "test-product",
                        1, 2, Arrays.asList("seller1", "seller2"),
                        "user"
                )));

        //When //Then
        assertThat(productService.update(
                new UpdateProductCommand(
                        new CreateProductCommand(
                                new CreateProductCommand.WriteProductPayload(
                                        "test-product",
                                        1, 2, Arrays.asList("seller1", "seller2")
                                ),
                                "user"
                        ),
                        "123"
                )
        ).get()).hasNoNullFieldsOrProperties();

        verify(productCrudPort).persist(productArgumentCaptor.capture());
        assertThat(productArgumentCaptor.getValue())
                .hasFieldOrPropertyWithValue("productName", "test-product")
                .hasFieldOrPropertyWithValue("amountAvailable", 1)
                .hasFieldOrPropertyWithValue("costInCents", 2)
                .hasFieldOrPropertyWithValue("sellerIds", Arrays.asList("seller1", "seller2"));
    }
}