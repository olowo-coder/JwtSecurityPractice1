package com.example.jwtsecuritydemo.configuration;

import com.example.jwtsecuritydemo.services.CustomUsersServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final CustomUsersServices usersServices;

    @Autowired
    public SecurityConfiguration(CustomUsersServices usersServices) {
        this.usersServices = usersServices;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //In-Memory Auth
        auth.inMemoryAuthentication().withUser("olowo")
                .password(passwordEncoder().encode("0987")).authorities("USER", "ADMIN");

        //Database Authentication
        auth.userDetailsService(usersServices).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /* This method permits all request without authentication*/
        http.csrf().disable().authorizeRequests().anyRequest().permitAll()
                .and().formLogin().disable();

        /* This method ensures all request are authentication
        http.authorizeRequests().anyRequest().authenticated();

        http.formLogin();
        http.httpBasic();*/

    }

    //    @Override
    //    protected AuthenticationManager authenticationManager() throws Exception {
    //        return super.authenticationManager();
    //    }
}
