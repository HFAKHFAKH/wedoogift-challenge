package com.wedoogift.challenge.service;

import com.wedoogift.challenge.exception.ApplicationException;
import com.wedoogift.challenge.entity.BalanceType;
import com.wedoogift.challenge.entity.User;
import com.wedoogift.challenge.reposotory.BalanceRepository;
import com.wedoogift.challenge.reposotory.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;


@SpringBootTest
class CalculateUserTicketServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private BalanceRepository balanceRepository;

    @InjectMocks
    private CalculateUserTicketService calculateUserTicketService = new CalculateUserTicketService(userRepository, balanceRepository);

    @Test
    @DisplayName("Test Mock CalculateUserTicketService - calculateMeal")
    void testCalculateMeal() {
        User user = new User();
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        Mockito.when(balanceRepository.calculateBalanceByType(user, BalanceType.MEAL)).thenReturn(100D);
        Double balance = calculateUserTicketService.calculateMeal(1L);
        Assertions.assertEquals(100, balance);
    }

    @Test
    @DisplayName("Test Mock CalculateUserTicketService - calculateMeal - user not found")
    void testCalculateMealUserNotFound() {
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.empty());
        ApplicationException exception = Assertions.assertThrows(ApplicationException.class, () -> calculateUserTicketService.calculateMeal(1L), "Expected calculateMeal() to throw, but it didn't");
        Assertions.assertEquals("User not found", exception.getMessage());
    }

    @Test
    @DisplayName("Test Mock CalculateUserTicketService - calculateGift")
    void testCalculateGift() {
        User user = new User();
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        Mockito.when(balanceRepository.calculateBalanceByType(user, BalanceType.GIFT)).thenReturn(100D);
        Double balance = calculateUserTicketService.calculateGift(1L);
        Assertions.assertEquals(100, balance);
    }

    @Test
    @DisplayName("Test Mock CalculateUserTicketService - calculateGift - user not found")
    void testCalculateGiftUserNotFound() {
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.empty());
        ApplicationException exception = Assertions.assertThrows(ApplicationException.class, () -> calculateUserTicketService.calculateGift(1L), "Expected calculateGift() to throw, but it didn't");
        Assertions.assertEquals("User not found", exception.getMessage());
    }
}
