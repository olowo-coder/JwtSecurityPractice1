package com.example.jwtsecuritydemo.controller;

import com.example.jwtsecuritydemo.entity.Authority;
import com.example.jwtsecuritydemo.entity.Users;
import com.example.jwtsecuritydemo.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RestController
public class AppController {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AppController(UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/")
    public String testApp(){
        return "Hello Spring Security";
    }

    @PostMapping("/add")
    public String addUsers(@RequestBody Users user){
        Set<Authority> authorityList = new HashSet<>();
        authorityList.add(createAuthority("USER","user role"));
        authorityList.add(createAuthority("ADMIN", "admin role"));
        user.setAuthorities(authorityList);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        usersRepository.save(user);
        return "Successfully added user";
    }

    private Authority createAuthority(String role, String roleDescription){
        Authority authority = new Authority();
        authority.setRoleCode(role);
        authority.setRoleDescription(roleDescription);
        return authority;
    }
}


//{
//        "username": "daniel090",
//        "firstName": "Daniel",
//        "lastName": "Crown",
//        "password": "$2a$12$XD4O8y7vL45hN6VBSmcZGOOdvp/9zkjc7mRo8tyxqpJSo/diNLSyC",
//        "email": "daniel@email.com",
//        "enabled":true,
//        "authorities": {
//        "roleCode": "USER",
//        "roleDescription": "user role"
//        }
//        }