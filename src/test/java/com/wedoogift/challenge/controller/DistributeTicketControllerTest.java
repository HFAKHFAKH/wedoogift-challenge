package com.wedoogift.challenge.controller;

import com.wedoogift.challenge.dto.ErrorDto;
import com.wedoogift.challenge.dto.UserBalanceDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DistributeTicketControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    @DisplayName("Test Mock DistributeTicketController - distributeMeal")
    void testDistributeMeal() {
        UserBalanceDto userBalanceDto = new UserBalanceDto();
        userBalanceDto.setUser(1L);
        userBalanceDto.setBalance(10D);
        ResponseEntity<Void> response = restTemplate.postForEntity(
                "http://localhost:" + port + "/distribute/meal", userBalanceDto, Void.class);
        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    @DisplayName("Test Mock DistributeTicketController - distributeGift")
    void testDistributeGift() {
        UserBalanceDto userBalanceDto = new UserBalanceDto();
        userBalanceDto.setUser(1L);
        userBalanceDto.setBalance(10D);
        ResponseEntity<Void> response = restTemplate.postForEntity(
                "http://localhost:" + port + "/distribute/gift", userBalanceDto, Void.class);
        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    @DisplayName("Test Mock DistributeTicketController - distributeGift - use not found")
    void testDistributeGiftUserNotFound() {
        UserBalanceDto userBalanceDto = new UserBalanceDto();
        userBalanceDto.setUser(20L);
        userBalanceDto.setBalance(10D);
        ResponseEntity<ErrorDto> response = restTemplate.postForEntity(
                "http://localhost:" + port + "/distribute/gift", userBalanceDto, ErrorDto.class);
        Assertions.assertTrue(response.getStatusCode().is4xxClientError());
        Assertions.assertEquals("User not found", Objects.requireNonNull(response.getBody()).getMessage());
    }

    @Test
    @DisplayName("Test Mock DistributeTicketController - distributeGift - Company balance gift not enough")
    void testDistributeGiftCompanyBalanceEnough() {
        UserBalanceDto userBalanceDto = new UserBalanceDto();
        userBalanceDto.setUser(1L);
        userBalanceDto.setBalance(1000D);
        ResponseEntity<ErrorDto> response = restTemplate.postForEntity(
                "http://localhost:" + port + "/distribute/gift", userBalanceDto, ErrorDto.class);
        Assertions.assertTrue(response.getStatusCode().is5xxServerError());
        Assertions.assertEquals("Company balance gift not enough", Objects.requireNonNull(response.getBody()).getMessage());
    }

}
