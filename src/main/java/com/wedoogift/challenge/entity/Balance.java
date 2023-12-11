package com.wedoogift.challenge.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity(name = "balance")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Balance {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Enumerated(EnumType.ORDINAL)
    private BalanceType balanceType;
    @ManyToOne
    private User user;
    private Double accountBalance;
    private LocalDate createdDate;
    private LocalDate endDate;
}
