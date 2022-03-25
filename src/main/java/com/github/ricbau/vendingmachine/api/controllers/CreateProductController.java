package com.github.ricbau.vendingmachine.api.controllers;

import com.github.ricbau.vendingmachine.api.controllers.mappers.ProductResultMapper;
import com.github.ricbau.vendingmachine.api.controllers.results.CreateProductResult;
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
import java.util.function.Function;

@RestController
@AllArgsConstructor
@RequestMapping("/products")
public class CreateProductController {

    private final CreateProductUseCase createProductUseCase;
    private final ProductResultMapper productResultMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateProductResult create(@RequestBody @Valid CreateProductCommand createProductCommand) {
        return createProductUseCase.create(createProductCommand)
                .map(productResultMapper::toResult)
                .getOrElseThrow(Function.identity());
    }

}
