package com.example.jwtsecuritydemo.entity;

import lombok.Data;

import java.util.Date;

@Data
public class UserInfo {
    private String firstName;
    private String lastName;
    private Object roles;
}
