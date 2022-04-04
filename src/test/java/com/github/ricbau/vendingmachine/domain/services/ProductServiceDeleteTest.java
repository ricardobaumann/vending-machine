package com.github.ricbau.vendingmachine.domain.services;

import com.github.ricbau.vendingmachine.domain.exceptions.DeleteProductException;
import com.github.ricbau.vendingmachine.domain.mappers.ProductCommandMapperImpl;
import com.github.ricbau.vendingmachine.domain.ports.ProductCrudPort;
import io.vavr.control.Try;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@DisplayName("When a product is being deleted")
@ExtendWith(MockitoExtension.class)
class ProductServiceDeleteTest {

    @Mock
    private ProductCrudPort productCrudPort;
    @Spy
    private ProductCommandMapperImpl productCommandMapper;
    @InjectMocks
    private ProductService productService;

    @Test
    @DisplayName("it should be deleted from repository")
    void delete() {
        //Given
        String id = "333";
        when(productCrudPort.delete(id))
                .thenReturn(Try.success(null));
        //When //Then
        assertThat(productService.delete(id).get())
                .isNull();
    }

    @Test
    @DisplayName("it should map repository exceptions")
    void exception() {
        //Given
        String id = "333";
        when(productCrudPort.delete(id))
                .thenReturn(Try.failure(
                        new RuntimeException()
                ));

        //When //Then
        assertThat(productService.delete(id).getLeft())
                .isInstanceOf(DeleteProductException.class)
                .hasCauseInstanceOf(RuntimeException.class);
    }
}