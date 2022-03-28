package com.github.ricbau.vendingmachine.api.controllers;

import com.github.ricbau.vendingmachine.domain.usecases.DeleteProductUseCase;
import io.vavr.control.Either;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("When a DELETE product request is handled")
@WebMvcTest(controllers = DeleteProductController.class)
class DeleteProductControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private DeleteProductUseCase deleteProductUseCase;

    @Test
    @WithMockUser
    @DisplayName("it should return NO CONTENT is successful")
    void deleteNoContent() throws Exception {
        //Given
        String id = "777";
        Mockito.when(deleteProductUseCase.delete(id))
                .thenReturn(Either.right(null));

        //When //Then
        mockMvc.perform(delete("/products/{id}", id))
                .andExpect(status().isNoContent());
    }
}