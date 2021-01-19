package com.pibox.swan.controller;

import com.pibox.swan.domain.User;
import com.pibox.swan.service.UserService;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{username}")
    public User getUser(@PathVariable("username") String username) {
        return userService.findUserByUsername(username);
    }
}
