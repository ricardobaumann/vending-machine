package com.github.ricbau.vendingmachine;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.ricbau.vendingmachine.domain.commands.CreateDepositCommand;
import com.github.ricbau.vendingmachine.domain.commands.CreateProductCommand;
import com.github.ricbau.vendingmachine.domain.commands.CreateUserCommand;
import com.github.ricbau.vendingmachine.persistence.entities.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class VendingMachineApplicationTests {
    
    @Test
    void contextLoads() {
        String username = "user";
        String password = "passwd";
        ObjectNode userResult = createUser(username, password);
        ObjectNode productResult = createProduct(username, password);
        assertThat(productResult.get("id").asText()).isNotBlank();

        createDeposit(username, password, userResult.get("id").asText());
    }

    void createDeposit(String username, String password, String userId) {
        TestRestTemplate testRestTemplate = new TestRestTemplate(username, password);
        testRestTemplate.postForObject("http://localhost:8080/deposits", new CreateDepositCommand(
                userId, 10
        ), ObjectNode.class);

        assertThat(Objects.requireNonNull(testRestTemplate.getForEntity("http://localhost:8080/users/{id}", ObjectNode.class, userId)
                .getBody()).get("balanceInCents").asInt()).isEqualTo(10);
    }

    ObjectNode createUser(String username, String password) {
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        return testRestTemplate.postForObject("http://localhost:8080/users", new CreateUserCommand(
                username,
                password,
                Collections.singleton(UserRole.USER)
        ), ObjectNode.class);
    }

    ObjectNode createProduct(String username, String password) {
        TestRestTemplate testRestTemplate = new TestRestTemplate(username, password);

        return testRestTemplate.postForObject("http://localhost:8080/products",
                new CreateProductCommand.WriteProductPayload(
                        "test-product",
                        10,
                        10,
                        Arrays.asList("sel1", "sel2")
                ), ObjectNode.class);
    }

}
