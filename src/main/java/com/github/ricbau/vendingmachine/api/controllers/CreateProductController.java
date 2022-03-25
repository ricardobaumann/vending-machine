package com.github.ricbau.vendingmachine.api.controllers;

import com.github.ricbau.vendingmachine.api.controllers.mappers.CreateResultMapper;
import com.github.ricbau.vendingmachine.api.controllers.results.WriteResult;
import com.github.ricbau.vendingmachine.domain.commands.CreateProductCommand;
import com.github.ricbau.vendingmachine.domain.usecases.CreateProductUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;
import java.util.function.Function;

@RestController
@AllArgsConstructor
@RequestMapping("/products")
public class CreateProductController {

    private final CreateProductUseCase createProductUseCase;
    private final CreateResultMapper createResultMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public WriteResult create(@RequestBody @Valid CreateProductCommand.WriteProductPayload writeProductPayload,
                              Principal principal) {
        return createProductUseCase.create(
                        new CreateProductCommand(
                                writeProductPayload,
                                principal.getName()
                        )
                )
                .map(createResultMapper::toResult)
                .getOrElseThrow(Function.identity());
    }

}
