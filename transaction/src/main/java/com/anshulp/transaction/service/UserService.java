package com.anshulp.transaction.service;

import com.anshulp.transaction.dto.UserResponse;
import com.anshulp.transaction.entity.User;
import com.anshulp.transaction.exception.UserNotFoundException;
import com.anshulp.transaction.mapper.UserMapper;
import com.anshulp.transaction.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Async
    public CompletableFuture<UserResponse> createUser(String name, String email) throws InterruptedException {
        if (userRepository.existsByEmail(email)) {
            throw new UserNotFoundException("User already exists with email: " + email);
        }
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        Thread.sleep(1000); // Simulate delay for async processing
        log.info("Creating user with email: {} and thread is {}", email, Thread.currentThread().getName());
        return CompletableFuture.completedFuture(UserMapper.toDto(userRepository.save(user)));
    }

    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional
    public UserResponse getUserById(Long id) {
        return UserMapper.toDto(userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found with id: " + id)));
    }


    public UserResponse updateUser(Long id, String name, String email) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        user.setName(name);
        user.setEmail(email);
        return UserMapper.toDto(userRepository.save(user));
    }

}
