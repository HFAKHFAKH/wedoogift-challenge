package com.wedoogift.challenge.controller;

import com.wedoogift.challenge.dto.BalanceDto;
import com.wedoogift.challenge.service.CalculateUserTicketService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/balance")
@AllArgsConstructor
public class CalculateBalanceController {

    private CalculateUserTicketService calculateUserTicketService;

    @GetMapping("/meal/{user}")
    public BalanceDto calculateMeal(@PathVariable("user") Long user) {
        Double balance = calculateUserTicketService.calculateMeal(user);
        return new BalanceDto(balance);
    }

    @GetMapping("/gift/{user}")
    public BalanceDto calculateGift(@PathVariable("user") Long user) {
        Double balance = calculateUserTicketService.calculateGift(user);
        return new BalanceDto(balance);
    }
}
