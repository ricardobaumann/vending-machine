package com.github.ricbau.vendingmachine.api.controllers;

import com.github.ricbau.vendingmachine.domain.exceptions.DeleteUserException;
import com.github.ricbau.vendingmachine.domain.usecases.DeleteUserUseCase;
import io.vavr.control.Either;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

@ExtendWith(SpringExtension.class)
@WebMvcTest(DeleteUserController.class)
class DeleteUserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DeleteUserUseCase mockDeleteUserUseCase;

    @Test
    @WithMockUser
    void testDelete() throws Exception {
        // Setup
        // Configure DeleteUserUseCase.delete(...).
        final Either<DeleteUserException, Void> voids = Either.right(null);
        when(mockDeleteUserUseCase.delete("id")).thenReturn(voids);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(delete("/users/{id}", "id")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}
