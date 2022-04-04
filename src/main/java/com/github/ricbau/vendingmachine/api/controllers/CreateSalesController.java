package com.github.ricbau.vendingmachine.api.controllers;

import com.github.ricbau.vendingmachine.api.controllers.mappers.SalesResultMapper;
import com.github.ricbau.vendingmachine.api.controllers.results.WriteResult;
import com.github.ricbau.vendingmachine.domain.commands.CreateSalesCommand;
import com.github.ricbau.vendingmachine.domain.usecases.CreateSaleUseCase;
import lombok.AllArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.function.Function;

@RestController
@AllArgsConstructor
@RequestMapping("/sales")
public class CreateSalesController {

    private final CreateSaleUseCase createSaleUseCase;
    private final SalesResultMapper salesResultMapper;

    @PostMapping
    @Secured("BUYER")
    public WriteResult post(@RequestBody @Valid CreateSalesCommand createSalesCommand) {
        return createSaleUseCase.create(createSalesCommand)
                .map(salesResultMapper::toResult)
                .getOrElseThrow(Function.identity());
    }
}
