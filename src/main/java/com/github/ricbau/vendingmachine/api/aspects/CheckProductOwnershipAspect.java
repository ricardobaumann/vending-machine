package com.github.ricbau.vendingmachine.api.aspects;

import com.github.ricbau.vendingmachine.domain.usecases.ProductOwnershipUseCase;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import java.security.Principal;
import java.util.Arrays;
import java.util.Optional;

@Slf4j
@Aspect
@Component
@AllArgsConstructor
public class CheckProductOwnershipAspect {

    private final ProductOwnershipUseCase productOwnershipUseCase;

    @Around("@annotation(com.github.ricbau.vendingmachine.api.aspects.ProductUpdateAllowed)")
    public Object measureAndLogTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        String username = Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(Principal::getName)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.FORBIDDEN));

        String productId = Arrays.stream(proceedingJoinPoint.getArgs())
                .filter(o -> o instanceof String)
                .map(o -> (String) o)
                .findFirst().orElseThrow(() -> new HttpClientErrorException(HttpStatus.FORBIDDEN));

        log.info("Checking if '{}' is the owner of product id '{}'", username, productId);

        if (!productOwnershipUseCase.isAllowedToWrite(username, productId)) {
            throw new HttpClientErrorException(HttpStatus.FORBIDDEN);
        }

        return proceedingJoinPoint.proceed();
    }

}
