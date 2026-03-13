package com.anshulp.springsecurity.config;

import com.anshulp.springsecurity.entity.Permission;
import com.anshulp.springsecurity.security.CustomUserDetailsService;
import com.anshulp.springsecurity.security.JwtAuthenticationFilter;
import com.anshulp.springsecurity.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(jwtUtil, userDetailsService);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .authorizeHttpRequests(auth -> auth
                                .requestMatchers("/auth/**").permitAll()
                                .requestMatchers(("/actuator/**")).permitAll()
//                        .requestMatchers(HttpMethod.GET, "/missions/all").hasAuthority(Permission.MISSION_READ.name())
//                        .requestMatchers(HttpMethod.POST, "/missions/create").hasAuthority(Permission.MISSION_CREATE.name())
//                        .requestMatchers(HttpMethod.DELETE, "/missions/delete/**").hasAuthority(Permission.MISSION_DELETE.name())
                                .anyRequest().authenticated()
                )

                .addFilterBefore(
                        jwtAuthenticationFilter(),
                        UsernamePasswordAuthenticationFilter.class
                );


        /*
            It is better to use method level authorization using @PreAuthorize and @PostAuthorize
            annotations on controller methods instead of defining authorization rules in the
            security configuration. This allows for more fine-grained control over access to
            specific methods and resources, and keeps the security configuration cleaner and more
            focused on authentication and general security settings.
        */

        return http.build();
    }
}



