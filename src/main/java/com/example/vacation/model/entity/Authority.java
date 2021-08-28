package com.example.vacation.model.entity;

import com.example.vacation.model.enums.RoleEnum;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Table(name = "roles")
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
public class Authority extends BaseEntity implements GrantedAuthority {
    @Enumerated(EnumType.STRING)
    private RoleEnum authority;

    @Override
    @Column(name = "role", nullable = false, unique = true)
    public String getAuthority() {
        return authority.name();
    }

    public void setAuthority(RoleEnum authority) {
        this.authority = authority;
    }

    public Authority() {
    }
}
