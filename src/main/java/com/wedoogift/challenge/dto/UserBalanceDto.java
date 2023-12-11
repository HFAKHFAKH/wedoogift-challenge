package com.wedoogift.challenge.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.NonNull;

@Getter
@Setter
public class UserBalanceDto {

    @NonNull
    private Long user;
    @NonNull
    private Double balance;
}
