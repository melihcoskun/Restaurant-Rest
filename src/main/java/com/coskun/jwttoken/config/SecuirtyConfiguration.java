package com.coskun.jwttoken.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.coskun.jwttoken.entity.Role.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecuirtyConfiguration {

    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req ->
                        req.requestMatchers("/crackit/v1/auth/*")
                                .permitAll()
                                .requestMatchers("/error").permitAll()
                                .requestMatchers("/crackit/v1/management/**").hasAnyRole(ADMIN.name(), MEMBER.name(), CUSTOMER.name())
                                .requestMatchers("/halabeni").hasAnyRole(ADMIN.name(),SUPERADMIN.name())
                                .requestMatchers("/restaurants/**").hasAnyRole(ADMIN.name(),SUPERADMIN.name())
                                .requestMatchers("/restaurants/myRestaurant/categories").hasAnyRole(ADMIN.name(),SUPERADMIN.name())
                                .requestMatchers("/myRestaurant/categories/{categoryId}/products").hasAnyRole(ADMIN.name(),SUPERADMIN.name())
                                .requestMatchers("/my-card").hasAnyRole(CUSTOMER.name())
                                .requestMatchers("/restaurants/myRestaurant").hasAnyRole(CUSTOMER.name())
                                .requestMatchers("/order/place").hasAnyRole("CUSTOMER")
                                .requestMatchers("/my-orders").hasAnyRole("CUSTOMER")
                                .requestMatchers("/orders").hasAnyRole("ADMIN")
                                .anyRequest()
                                .authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();


    }

}
