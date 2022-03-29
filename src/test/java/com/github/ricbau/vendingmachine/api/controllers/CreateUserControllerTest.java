package com.github.ricbau.vendingmachine.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.ricbau.vendingmachine.api.controllers.mappers.CreateResultMapperImpl;
import com.github.ricbau.vendingmachine.domain.commands.CreateUserCommand;
import com.github.ricbau.vendingmachine.domain.entities.User;
import com.github.ricbau.vendingmachine.domain.usecases.CreateUserUseCase;
import com.github.ricbau.vendingmachine.persistence.entities.UserRole;
import io.vavr.control.Either;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("When a user POST request is handled")
@WebMvcTest(controllers = CreateUserController.class)
class CreateUserControllerTest {

    @MockBean
    private CreateUserUseCase createUserUseCase;
    @SpyBean
    private CreateResultMapperImpl createResultMapper;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    
    @Test
    @DisplayName("it should create and return the user id")
    void postCreated() throws Exception {
        //Given
        CreateUserCommand createUserCommand = new CreateUserCommand(
                "user", "passwd", Collections.singleton(UserRole.USER)
        );
        when(createUserUseCase.create(
                createUserCommand
        )).thenReturn(Either.right(new User(
                "44", "user", "passwd", 0,
                Collections.singleton(UserRole.USER)
        )));

        //When //Then
        mockMvc.perform(
                        post("/users")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(createUserCommand))
                ).andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", Is.is("44")))
                .andExpect(jsonPath("$.location", Is.is("http://localhost/users/44")));
    }

    @Test
    @DisplayName("it should return BAD_REQUEST on invalid input")
    void postBadRequest() throws Exception {
        //Given
        CreateUserCommand createUserCommand = new CreateUserCommand(
                null, "passwd", Collections.singleton(UserRole.USER)
        );

        //When //Then
        mockMvc.perform(
                post("/users")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createUserCommand))
        ).andExpect(status().isBadRequest());

        verify(createUserUseCase, never()).create(any());
    }
}