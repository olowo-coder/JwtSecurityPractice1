package com.example.jwtsecuritydemo.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
//@Table(name = "AUTH_AUTHORITY")
public class Authority implements GrantedAuthority {
    @Id
    @SequenceGenerator(
            name = "authority_sequence",
            sequenceName = "authority_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "authority_sequence"
    )
    private Long id;
    private String roleCode;
    private String roleDescription;


    @Override
    public String getAuthority() {
        return roleCode;
    }
}
