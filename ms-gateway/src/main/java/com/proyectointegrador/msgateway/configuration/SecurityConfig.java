package com.proyectointegrador.msgateway.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity server) {
        server
                .authorizeExchange(authorize -> authorize
                        .pathMatchers("api/user/**").authenticated()
                        .pathMatchers("api/ticket/tickets/**").authenticated()
                        .pathMatchers("api/ticket/paymentMethod/**").permitAll()
                        .pathMatchers("api/place/city/public/**").permitAll()
                        .pathMatchers("api/place/city/private/**").authenticated()
                        .pathMatchers("api/place/place/public/**").permitAll()
                        .pathMatchers("api/place/place/private/**").authenticated()
                        .pathMatchers("api/place/zone/public/**").permitAll()
                        .pathMatchers("api/place/zone/private/**").authenticated()
                        .pathMatchers("api/place/seat/public/**").permitAll()
                        .pathMatchers("api/place/seat/private/**").authenticated()
                        .anyExchange().authenticated()
                )
                .oauth2Login();
        return server.build();
    }
}
