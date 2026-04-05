package com.anshulp.transaction.controller;

import com.anshulp.transaction.dto.UserResponse;
import com.anshulp.transaction.entity.Order;
import com.anshulp.transaction.entity.User;
import com.anshulp.transaction.mapper.UserMapper;
import com.anshulp.transaction.repository.UserRepository;
import com.anshulp.transaction.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    @PostMapping("/users")
    public CompletableFuture<ResponseEntity<UserResponse>> createUser(@RequestParam String name, @RequestParam String email) throws InterruptedException {
        return userService.createUser(name, email)
                .thenApply(ResponseEntity::ok)
                .exceptionally(ex ->
                        ResponseEntity.badRequest().build()
                );
    }

    @GetMapping("/users/{id}")
    public UserResponse getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PostMapping("/addP/{id}")
    public UserResponse updateUser(@PathVariable Long id, @RequestParam String productName, @RequestParam double amount) {

        User user = User.builder()
                .name("anshi")
                .email("anshi@gmail.com")
                .orders(new ArrayList<>())
                .build();

        Order order = Order.builder()
                .productName(productName)
                .amount(amount)
                .user(user)
                .build();
        user.getOrders().add(order);
        return UserMapper.toDto(userRepository.save(user));

    }

    @PostMapping("/addM/{id}")
    public UserResponse updateUser2(@PathVariable Long id, @RequestParam String productName, @RequestParam double amount) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        Order order = Order.builder()
                .productName(productName)
                .amount(amount)
                .user(user)
                .build();
        user.getOrders().add(order);
        return UserMapper.toDto(userRepository.save(user));
    }


    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @DeleteMapping("/deleteLast/{id}")
    public void deleteLastOrder(@PathVariable Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        List<Order> orders = user.getOrders();
        if (!orders.isEmpty()) {
            orders.remove(orders.size() - 1);
            user.setOrders(orders);
            userRepository.save(user);
        }
    }
}
