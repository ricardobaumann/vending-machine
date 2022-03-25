package com.github.ricbau.vendingmachine.api.controllers;

import com.github.ricbau.vendingmachine.api.aspects.CheckProductOwnership;
import com.github.ricbau.vendingmachine.api.controllers.mappers.UpdateResultMapper;
import com.github.ricbau.vendingmachine.api.controllers.results.WriteResult;
import com.github.ricbau.vendingmachine.domain.commands.CreateProductCommand;
import com.github.ricbau.vendingmachine.domain.commands.CreateProductCommand.WriteProductPayload;
import com.github.ricbau.vendingmachine.domain.commands.UpdateProductCommand;
import com.github.ricbau.vendingmachine.domain.usecases.UpdateProductUseCase;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;
import java.util.function.Function;

@RestController
@AllArgsConstructor
@RequestMapping("/products/{id}")
public class UpdateProductController {

    private final UpdateProductUseCase updateProductUseCase;
    private final UpdateResultMapper resultMapper;

    @PutMapping
    @CheckProductOwnership
    public WriteResult put(@RequestBody @Valid WriteProductPayload writeProductPayload,
                           @PathVariable String id,
                           Principal principal) {
        return updateProductUseCase.update(
                        new UpdateProductCommand(
                                new CreateProductCommand(writeProductPayload, principal.getName()),
                                id
                        )
                ).map(resultMapper::toResult)
                .getOrElseThrow(Function.identity());
    }
}
