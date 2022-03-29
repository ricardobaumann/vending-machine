package com.github.ricbau.vendingmachine.api.controllers;

import com.github.ricbau.vendingmachine.api.controllers.mappers.UserResultMapper;
import com.github.ricbau.vendingmachine.api.controllers.results.UserResult;
import com.github.ricbau.vendingmachine.domain.usecases.ReadUserUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class ReadUserController {

    private final ReadUserUseCase readUserUseCase;
    private final UserResultMapper userResultMapper;

    @GetMapping("/{id}")
    public ResponseEntity<UserResult> get(@PathVariable String id) {
        return readUserUseCase.findByID(id)
                .map(userResultMapper::toResult)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<UserResult> get() {
        return readUserUseCase.getAll()
                .stream().map(userResultMapper::toResult)
                .collect(Collectors.toList());
    }

}
