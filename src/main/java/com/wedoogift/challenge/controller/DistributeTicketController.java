package com.wedoogift.challenge.controller;

import com.wedoogift.challenge.dto.UserBalanceDto;
import com.wedoogift.challenge.service.DistributeTicketService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/distribute")
@AllArgsConstructor
public class DistributeTicketController {

    private DistributeTicketService distributeTicketService;

    @PostMapping("/meal")
    public void distributeMeal(@RequestBody UserBalanceDto userBalanceDto) {
        distributeTicketService.distributeMeal(userBalanceDto.getUser(), userBalanceDto.getBalance());
    }

    @PostMapping("/gift")
    public void distributeGift(@RequestBody UserBalanceDto userBalanceDto) {
        distributeTicketService.distributeGift(userBalanceDto.getUser(), userBalanceDto.getBalance());
    }
}
