package com.github.ricbau.vendingmachine.api.controllers;

import com.github.ricbau.vendingmachine.api.aspects.CheckProductPermissions;
import com.github.ricbau.vendingmachine.domain.usecases.DeleteProductUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.function.Function;

@RestController
@AllArgsConstructor
@CheckProductPermissions
@RequestMapping("/products/{id}")
public class DeleteProductController {

    private final DeleteProductUseCase deleteProductUseCase;

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        deleteProductUseCase.delete(id)
                .getOrElseThrow(Function.identity());
    }

}
