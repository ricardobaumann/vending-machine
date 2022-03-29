package com.github.ricbau.vendingmachine.domain.mappers;

import com.github.ricbau.vendingmachine.api.security.CustomPasswordEncoder;
import com.github.ricbau.vendingmachine.domain.entities.Password;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
@SuppressWarnings("unused")
public class PasswordMapper {

    private final CustomPasswordEncoder customPasswordEncoder;

    public Password encrypt(String password) {
        log.info("Encoding password: {}", password);
        return new Password(
                customPasswordEncoder.encode(password)
        );
    }

}
