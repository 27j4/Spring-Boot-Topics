package com.anshulp.springsecurity.config;

import com.anshulp.springsecurity.entity.Role;
import com.anshulp.springsecurity.entity.User;
import com.anshulp.springsecurity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        if (!userRepository.existsByUsername("anshulAdmin")) {
            User defaultAdmin = User.builder()
                    .username("anshulAdmin")
                    .email("anshulp@google.com")
                    .password(passwordEncoder.encode("admin@123"))
                    .role(Role.ADMIN)
                    .build();

            userRepository.save(defaultAdmin);
        }
    }
}


