package com.wedoogift.challenge.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "user")
@Getter
@Setter
public class User {

    @Id
    private Long id;
    private String name;
    @ManyToOne
    private Company company;


}
