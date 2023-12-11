package com.wedoogift.challenge.controller;

import com.wedoogift.challenge.dto.BalanceDto;
import com.wedoogift.challenge.dto.ErrorDto;
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
class CalculateBalanceControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @DisplayName("Test Mock CalculateBalanceController - calculateMeal")
    void testCalculateMeal() {
        ResponseEntity<BalanceDto> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/balance/meal/1", BalanceDto.class);
        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
        Assertions.assertEquals(0D, Objects.requireNonNull(response.getBody()).getBalance());
    }

    @Test
    @DisplayName("Test Mock CalculateBalanceController - calculateGift")
    void testCalculateGift() {
        ResponseEntity<BalanceDto> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/balance/gift/1", BalanceDto.class);
        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
        Assertions.assertEquals(0D, Objects.requireNonNull(response.getBody()).getBalance());
    }

    @Test
    @DisplayName("Test Mock CalculateBalanceController - calculateGift - use not found")
    void testDistributeGiftUserNotFound() {
        ResponseEntity<ErrorDto> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/balance/gift/20", ErrorDto.class);
        Assertions.assertTrue(response.getStatusCode().is4xxClientError());
        Assertions.assertEquals("User not found", Objects.requireNonNull(response.getBody()).getMessage());
    }
}
