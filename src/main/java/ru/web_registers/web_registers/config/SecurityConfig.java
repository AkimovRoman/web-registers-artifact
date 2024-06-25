package ru.web_registers.web_registers.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .formLogin(form -> form
                        .loginPage("/login")
                        .permitAll()
                );
        return http.build();
    }
}
