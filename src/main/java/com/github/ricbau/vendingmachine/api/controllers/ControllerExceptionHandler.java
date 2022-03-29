package com.github.ricbau.vendingmachine.api.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.zalando.problem.spring.web.advice.ProblemHandling;

@ControllerAdvice
public class ControllerExceptionHandler implements ProblemHandling {

}
