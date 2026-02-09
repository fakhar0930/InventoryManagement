package com.inventorymanagement.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public InMemoryUserDetailsManager userDetailsManager(){
        UserDetails user1= User.builder()
                .username("user1")
                .password(passwordEncoder().encode("user1"))
                .roles("USER")
                .build();

        UserDetails user2= User.builder()
                .username("user2")
                .password(passwordEncoder().encode("user2"))
                .roles("USER")
                .build();

        UserDetails user3= User.builder()
                .username("user3")
                .password(passwordEncoder().encode("user3"))
                .roles("USER")
                .build();


        UserDetails admin= User.builder()
                .username("admin")
                .password(passwordEncoder().encode("admin"))
                .roles("ADMIN")
                .build();


        return new InMemoryUserDetailsManager(user1,user2,user3,admin);
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth

                        .requestMatchers(HttpMethod.GET, "/products/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.PATCH,"/products/buy").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/products/add").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/products/update-stock").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/products/delete/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults()); // Uses Basic Auth (Username/Password)

        return http.build();
    }

}
