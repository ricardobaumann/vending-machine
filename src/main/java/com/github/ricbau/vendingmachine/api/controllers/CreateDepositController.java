package com.github.ricbau.vendingmachine.api.controllers;

import com.github.ricbau.vendingmachine.api.controllers.mappers.DepositResultMapper;
import com.github.ricbau.vendingmachine.api.controllers.results.CreateDepositResult;
import com.github.ricbau.vendingmachine.domain.commands.CreateDepositCommand;
import com.github.ricbau.vendingmachine.domain.usecases.CreateDepositUseCase;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.function.Function;

@RestController
@AllArgsConstructor
@RequestMapping("/deposits")
public class CreateDepositController {

    private final CreateDepositUseCase createDepositUseCase;
    private final DepositResultMapper depositResultMapper;

    @PostMapping
    public CreateDepositResult post(@RequestBody @Valid CreateDepositCommand createDepositCommand) {
        return createDepositUseCase.create(createDepositCommand)
                .map(depositResultMapper::toResult)
                .getOrElseThrow(Function.identity());
    }

}
