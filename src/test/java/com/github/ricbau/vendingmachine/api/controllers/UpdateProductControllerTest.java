package com.github.ricbau.vendingmachine.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.ricbau.vendingmachine.api.controllers.mappers.UpdateResultMapperImpl;
import com.github.ricbau.vendingmachine.domain.commands.CreateProductCommand;
import com.github.ricbau.vendingmachine.domain.commands.UpdateProductCommand;
import com.github.ricbau.vendingmachine.domain.entities.Product;
import com.github.ricbau.vendingmachine.domain.usecases.UpdateProductUseCase;
import io.vavr.control.Either;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("When a PUT product request is handled")
@WebMvcTest(controllers = UpdateProductController.class)
class UpdateProductControllerTest {

    @MockBean
    private UpdateProductUseCase updateProductUseCase;
    @SpyBean
    private UpdateResultMapperImpl updateResultMapper;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    
    @Test
    @WithMockUser
    @DisplayName("it should return OK on a successful operation")
    void putOK() throws Exception {
        //Given
        CreateProductCommand.WriteProductPayload writeProductPayload = new CreateProductCommand.WriteProductPayload(
                "test-product",
                1, 1, Arrays.asList("seller1", "seller2")
        );
        CreateProductCommand createProductCommand = new CreateProductCommand(
                writeProductPayload, "user"
        );

        String id = "999";
        when(updateProductUseCase.update(
                        new UpdateProductCommand(createProductCommand, id)
                )
        )
                .thenReturn(Either.right(new Product(
                        "100", "test-product",
                        1, 1, Arrays.asList("seller1", "seller2"),
                        "user"
                )));

        //When //Then
        mockMvc.perform(MockMvcRequestBuilders.put("/products/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(writeProductPayload))
        ).andExpect(status().isOk());
    }
}