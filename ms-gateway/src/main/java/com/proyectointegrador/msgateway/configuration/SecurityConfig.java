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
                        .pathMatchers("api/events/category/public/**").permitAll()
                        .pathMatchers("api/events/category/private/**").authenticated()
                        .pathMatchers("api/events/event/public/**").permitAll()
                        .pathMatchers("api/events/event/private/**").authenticated()
                        .pathMatchers("api/events/dateEvent/public/**").permitAll()
                        .pathMatchers("api/events/dateEvent/private/**").authenticated()
                        .anyExchange().authenticated()
                )
                .oauth2Login();
        return server.build();
    }
}
