package com.example.vacation.service.impl;

import com.example.vacation.model.entity.Authority;
import com.example.vacation.model.enums.RoleEnum;
import com.example.vacation.repository.AuthorityRepository;
import com.example.vacation.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorityServiceImpl implements AuthorityService {
    private final AuthorityRepository authorityRepository;

    @Autowired
    public AuthorityServiceImpl(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    @Override
    public void seedAuthorities() {
        for (int i = 0; i < RoleEnum.values().length; i++) {
            Authority role = new Authority(RoleEnum.values()[i]);
            this.authorityRepository.saveAndFlush(role);
        }
    }
}
