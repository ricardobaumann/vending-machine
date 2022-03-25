package com.github.ricbau.vendingmachine.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.ricbau.vendingmachine.api.controllers.mappers.CreateResultMapperImpl;
import com.github.ricbau.vendingmachine.domain.commands.CreateProductCommand;
import com.github.ricbau.vendingmachine.domain.commands.CreateProductCommand.WriteProductPayload;
import com.github.ricbau.vendingmachine.domain.entities.Product;
import com.github.ricbau.vendingmachine.domain.usecases.CreateProductUseCase;
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

import java.util.Arrays;

import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("When a product POST request is being handled")
@WebMvcTest(controllers = CreateProductController.class)
class CreateProductControllerCreateTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CreateProductUseCase createProductUseCase;
    @SpyBean
    private CreateResultMapperImpl productResultMapper;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser
    @DisplayName("it should create and return the id and location if successful")
    void create() throws Exception {
        //Given
        WriteProductPayload writeProductPayload = new WriteProductPayload(
                "test-product",
                1, 1, Arrays.asList("seller1", "seller2")
        );
        CreateProductCommand createProductCommand = new CreateProductCommand(
                writeProductPayload, "user"
        );
        when(createProductUseCase.create(
                createProductCommand
        )).thenReturn(
                Either.right(new Product(
                        "100", "test-product",
                        1, 1, Arrays.asList("seller1", "seller2"),
                        "user"
                )));

        //When //Then
        mockMvc.perform(
                        post("/products")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(writeProductPayload))
                ).andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is("100")))
                .andExpect(jsonPath("$.location", is("http://localhost/products/100")));
    }

    @Test
    @WithMockUser
    @DisplayName("it should return BAD_REQUEST is the input payload is not valid")
    void validate() throws Exception {
        //When //Then
        mockMvc.perform(
                post("/products")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}")
        ).andExpect(status().isBadRequest());

        verify(createProductUseCase, never()).create(any());
    }
}
