package com.vgs.vocation_guidance.controller;

import com.vgs.vocation_guidance.entity.User;
import com.vgs.vocation_guidance.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.getUser(id).orElseThrow(() -> new RuntimeException("User not found"));
    }
}
