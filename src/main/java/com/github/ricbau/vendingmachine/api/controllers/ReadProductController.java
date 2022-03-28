package com.github.ricbau.vendingmachine.api.controllers;

import com.github.ricbau.vendingmachine.api.controllers.mappers.ProductResultMapper;
import com.github.ricbau.vendingmachine.api.controllers.results.ProductResult;
import com.github.ricbau.vendingmachine.domain.usecases.ReadProductUseCase;
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
@RequestMapping("/products")
public class ReadProductController {

    private final ReadProductUseCase readProductUseCase;
    private final ProductResultMapper productResultMapper;

    @GetMapping("/{id}")
    public ResponseEntity<ProductResult> get(@PathVariable String id) {
        return readProductUseCase.findById(id)
                .map(productResultMapper::toResult)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<ProductResult> get() {
        return readProductUseCase.getAll()
                .stream().map(productResultMapper::toResult)
                .collect(Collectors.toList());
    }

}
