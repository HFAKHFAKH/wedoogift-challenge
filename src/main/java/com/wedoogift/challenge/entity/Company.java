package com.wedoogift.challenge.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "company")
@Getter
@Setter
public class Company {

    @Id
    private Long id;
    private String name;
    private Double balanceMeal;
    private Double balanceGift;

}
