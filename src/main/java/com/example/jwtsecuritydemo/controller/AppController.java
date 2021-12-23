package com.example.jwtsecuritydemo.controller;

import com.example.jwtsecuritydemo.entity.Authority;
import com.example.jwtsecuritydemo.entity.Users;
import com.example.jwtsecuritydemo.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AppController {

    private final UsersRepository usersRepository;

    @Autowired
    public AppController(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @GetMapping("/")
    public String testApp(){
        return "Hello Spring Security";
    }

    @PostMapping("/add")
    public String addUsers(@RequestBody Users user){
        List<Authority> authorityList = new ArrayList<>();
        authorityList.add(createAuthority("USER","user role"));
        authorityList.add(createAuthority("ADMIN", "admin role"));

        user.setAuthorities(authorityList);
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