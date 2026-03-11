package com.anshulp.springsecurity.config;

import com.anshulp.springsecurity.entity.Role;
import com.anshulp.springsecurity.entity.User;
import com.anshulp.springsecurity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private static final String DEFAULT_ADMIN_USERNAME = "anshulAdmin";
    private static final String DEFAULT_ADMIN_EMAIL = "anshulp@google.com";

    @Value("${admin.default.password:Admin@123}")
    private String defaultAdminPassword;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (!userRepository.existsByUsername(DEFAULT_ADMIN_USERNAME)) {
            User admin = User.builder()
                    .username(DEFAULT_ADMIN_USERNAME)
                    .email(DEFAULT_ADMIN_EMAIL)
                    .password(passwordEncoder.encode(defaultAdminPassword))
                    .role(Role.ADMIN)
                    .build();

            userRepository.save(admin);
            log.info("Default admin user '{}' created successfully.", DEFAULT_ADMIN_USERNAME);
        } else {
            log.info("Default admin user '{}' already exists. Skipping creation.", DEFAULT_ADMIN_USERNAME);
        }
    }
}
