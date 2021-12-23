package com.example.jwtsecuritydemo;

import com.example.jwtsecuritydemo.entity.Authority;
import com.example.jwtsecuritydemo.entity.Users;
import com.example.jwtsecuritydemo.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class JwtSecurityDemoApplication {
    private PasswordEncoder passwordEncoder;
    private UsersRepository usersRepository;

    @Autowired
    public JwtSecurityDemoApplication(PasswordEncoder passwordEncoder, UsersRepository usersRepository) {
        this.passwordEncoder = passwordEncoder;
        this.usersRepository = usersRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(JwtSecurityDemoApplication.class, args);
    }

    @PostConstruct
    protected void init(){
        List<Authority> authorityList = new ArrayList<>();
        authorityList.add(createAuthority("USER","user role"));
        authorityList.add(createAuthority("ADMIN", "admin role"));

        Users user = new Users();
        user.setUsername("olowo123");
        user.setFirstName("olowo");
        user.setLastName("ben");
        user.setPassword(passwordEncoder.encode("lanre"));
        user.setEnabled(true);
//        user.setAuthorities(authorityList);

        usersRepository.save(user);

    }

    private Authority createAuthority(String role, String roleDescription){
        Authority authority = new Authority();
        authority.setRoleCode(role);
        authority.setRoleDescription(roleDescription);
        return authority;
    }

}
