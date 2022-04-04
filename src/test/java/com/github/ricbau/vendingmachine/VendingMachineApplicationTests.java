package com.github.ricbau.vendingmachine;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.ricbau.vendingmachine.domain.commands.CreateDepositCommand;
import com.github.ricbau.vendingmachine.domain.commands.CreateProductCommand;
import com.github.ricbau.vendingmachine.domain.commands.CreateSalesCommand;
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
        String productId = productResult.get("id").asText();
        String productAddress = productResult.get("location").asText();
        assertThat(productId).isNotBlank();

        String userId = userResult.get("id").asText();
        createDeposit(username, password, userId);

        createSale(username, password, productId, userId, productAddress);
    }

    private void createSale(String username, String password, String productId, String userId, String productAddress) {
        TestRestTemplate testRestTemplate = new TestRestTemplate(username, password);

        String address = testRestTemplate.postForObject("http://localhost:8080/sales", new CreateSalesCommand(
                productId, 10, userId
        ), ObjectNode.class).get("location").asText();
        
        assertThat(testRestTemplate.getForObject(productAddress, ObjectNode.class).get("amountAvailable").asInt())
                .isZero();
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
