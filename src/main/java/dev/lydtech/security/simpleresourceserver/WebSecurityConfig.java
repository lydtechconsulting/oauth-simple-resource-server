package dev.lydtech.security.simpleresourceserver;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final JwtAuthConverter jwtAuthConverter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                //Don't have to be authenticated to call anything under public
                .requestMatchers(HttpMethod.GET, "/api/v1/public", "/api/v1/public/**").permitAll()
                //Only admins can access anything under admin
                .requestMatchers(HttpMethod.GET, "/api/v1/admin", "/api/v1/admin/**").hasRole("admin")
                //Users and admins can access user endpoints
                .requestMatchers(HttpMethod.GET, "/api/v1/user").hasAnyRole("admin", "user")
                .anyRequest().authenticated();
        http.oauth2ResourceServer()
                .jwt()
                .jwtAuthenticationConverter(jwtAuthConverter);
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.cors();
        return http.build();
    }

}