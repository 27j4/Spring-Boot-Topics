package com.anshulp.mailservice.controller;

import com.anshulp.mailservice.entity.User;
import com.anshulp.mailservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/create")
    public User createUser(@RequestParam String name, @RequestParam String email){
        return userService.createUser(name, email);
    }

    @PostMapping("/swa")
    public String swa(@RequestParam String name, @RequestParam String email){
        return userService.swa(name, email);
    }

    @GetMapping("/get")
    public User getUser(@RequestParam String email) {
        return userService.getUserByEmail(email);
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam String email) {
        return userService.deleteUser(email);
    }
}

