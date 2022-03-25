package com.github.ricbau.vendingmachine.api.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.zalando.problem.jackson.ProblemModule;
import org.zalando.problem.violations.ConstraintViolationProblemModule;

import javax.annotation.PostConstruct;

@Configuration
@AllArgsConstructor
public class ObjectMapperConfig {

    private final ObjectMapper objectMapper;

    @PostConstruct
    public void init() {
        objectMapper.registerModules(
                new ProblemModule(),
                new ConstraintViolationProblemModule());
    }

}
