package com.wedoogift.challenge.service;

import com.wedoogift.challenge.exception.ApplicationException;
import com.wedoogift.challenge.entity.BalanceType;
import com.wedoogift.challenge.entity.User;
import com.wedoogift.challenge.reposotory.BalanceRepository;
import com.wedoogift.challenge.reposotory.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CalculateUserTicketService {

    private UserRepository userRepository;
    private BalanceRepository balanceRepository;


    public Double calculateMeal(Long userId) {
        return calculateBalance(userId, BalanceType.MEAL);
    }

    public Double calculateGift(Long userId) {
        return calculateBalance(userId, BalanceType.GIFT);
    }

    private Double calculateBalance(Long userId, BalanceType balanceType) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new ApplicationException(404, "User not found");
        }
        return balanceRepository.calculateBalanceByType(userOptional.get(), balanceType);
    }
}
