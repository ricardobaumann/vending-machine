package com.github.ricbau.vendingmachine.api.controllers;

import com.github.ricbau.vendingmachine.domain.usecases.DeleteUserUseCase;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.function.Function;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class DeleteUserController {

    private final DeleteUserUseCase deleteUserUseCase;

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        deleteUserUseCase.delete(id)
                .getOrElseThrow(Function.identity());
    }

}
