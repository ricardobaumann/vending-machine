package com.github.ricbau.vendingmachine.api.controllers;

import com.github.ricbau.vendingmachine.api.controllers.mappers.CreateResultMapper;
import com.github.ricbau.vendingmachine.api.controllers.results.WriteResult;
import com.github.ricbau.vendingmachine.domain.commands.CreateUserCommand;
import com.github.ricbau.vendingmachine.domain.usecases.CreateUserUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.function.Function;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class CreateUserController {

    private final CreateUserUseCase createUserUseCase;
    private final CreateResultMapper createResultMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public WriteResult post(@RequestBody @Valid CreateUserCommand createUserCommand) {
        return createUserUseCase.create(createUserCommand)
                .map(createResultMapper::toResult)
                .getOrElseThrow(Function.identity());
    }

}
