package com.example.vacation.model.service;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class EmployeeServiceModel {

    private Long id;

    private String username;

    private String password;

    private String email;

    private String firstName;

    private String lastName;

    private LocalDateTime registerOn;

    private Integer phone;

    private boolean active;

    private Set<AuthorityServiceModel> authorities;
}
