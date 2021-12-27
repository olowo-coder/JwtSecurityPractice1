package com.example.jwtsecuritydemo.configuration;

import com.example.jwtsecuritydemo.services.CustomUsersServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final CustomUsersServices usersServices;
    private final JwtTokenHelper jwtTokenHelper;
    private final AuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    public SecurityConfiguration(CustomUsersServices usersServices, JwtTokenHelper jwtTokenHelper, AuthenticationEntryPoint authenticationEntryPoint) {
        this.usersServices = usersServices;
        this.jwtTokenHelper = jwtTokenHelper;
        this.authenticationEntryPoint = authenticationEntryPoint;
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
        /* This method permits all request without authentication
        http.csrf().disable().authorizeRequests().anyRequest().permitAll()
                .and().formLogin().disable();*/

        // This method ensures all request are authentication
//        http.authorizeRequests().anyRequest().authenticated();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                        .and().exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)
                        .and().authorizeRequests((request) -> request.antMatchers("/h2-console/**","/auth/login").permitAll()
                        .antMatchers(HttpMethod.OPTIONS,"/**").permitAll().anyRequest().authenticated())
                        .addFilterBefore(new JwtAuthenticationFilter(usersServices, jwtTokenHelper)
                        , UsernamePasswordAuthenticationFilter.class);

//        http.formLogin();
        http.cors().disable();
        http.csrf().disable().headers().frameOptions().disable();
        http.httpBasic();

    }

        @Bean
        @Override
        public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
        }
}



