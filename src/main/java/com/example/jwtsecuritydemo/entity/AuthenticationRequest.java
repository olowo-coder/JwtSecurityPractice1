package com.example.jwtsecuritydemo.entity;

import lombok.Data;
import lombok.Getter;

@Data
public class AuthenticationRequest {
    private String userName;
    private String password;
}
