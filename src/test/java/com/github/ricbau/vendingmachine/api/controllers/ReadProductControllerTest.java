package com.github.ricbau.vendingmachine.api.controllers;

import com.github.ricbau.vendingmachine.api.controllers.mappers.ProductResultMapperImpl;
import com.github.ricbau.vendingmachine.domain.entities.Product;
import com.github.ricbau.vendingmachine.domain.usecases.ReadProductUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("When a GET products request is handled")
@WebMvcTest(controllers = ReadProductController.class)
class ReadProductControllerTest {

    @MockBean
    private ReadProductUseCase readProductUseCase;
    @SpyBean
    private ProductResultMapperImpl productResultMapper;
    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    @DisplayName("it should return a product by id if present")
    void getByIdFound() throws Exception {
        //Given
        String id = "1";
        when(readProductUseCase.findById(id))
                .thenReturn(
                        Optional.of(new Product(
                                "1", "test", 1, 2,
                                Collections.singletonList("sel1"), "user"
                        ))
                );
        //When //Then
        mockMvc.perform(get("/products/{id}", id)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(id)))
                .andExpect(jsonPath("$.productName", is("test")))
                .andExpect(jsonPath("$.amountAvailable", is(1)))
                .andExpect(jsonPath("$.costInCents", is(2)))
                .andExpect(jsonPath("$.sellerIds[0]", is("sel1")));
    }

    @Test
    @WithMockUser
    @DisplayName("it should NOT_FOUND if the product is is absent")
    void getByIdNotFound() throws Exception {
        //Given
        String id = "1";
        when(readProductUseCase.findById(id))
                .thenReturn(Optional.empty());

        //When //Then
        mockMvc.perform(get("/products/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    @DisplayName("it should return a list of products")
    void getAll() throws Exception {
        //Given
        when(readProductUseCase.getAll())
                .thenReturn(
                        Collections.singletonList(
                                new Product(
                                        "1", "test", 1, 2,
                                        Collections.singletonList("sel1"), "user"
                                )
                        )
                );

        //When //Then
        mockMvc.perform(get("/products")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is("1")))
                .andExpect(jsonPath("$[0].productName", is("test")))
                .andExpect(jsonPath("$[0].amountAvailable", is(1)))
                .andExpect(jsonPath("$[0].costInCents", is(2)))
                .andExpect(jsonPath("$[0].sellerIds[0]", is("sel1")));
    }
}