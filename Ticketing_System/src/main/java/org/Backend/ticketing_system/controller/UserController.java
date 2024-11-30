package org.Backend.ticketing_system.controller;

import org.Backend.ticketing_system.model.User;
import org.Backend.ticketing_system.repositary.UserRepositary;
import org.Backend.ticketing_system.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public String signup(String username,String email, String password) {
        User newUser = userService.Usersignup(username, email, password);
        return "User signup successful";
    }
}
