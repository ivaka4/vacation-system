package com.example.vacation.repository;

import com.example.vacation.model.entity.Authority;
import com.example.vacation.model.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {

    Set<Authority> findAllByAuthority(RoleEnum roleEnum);

    Authority findByAuthority(RoleEnum roleEnum);
}
