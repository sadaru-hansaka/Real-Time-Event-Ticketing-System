package org.Backend.ticketing_system.service;

import org.Backend.ticketing_system.model.User;
import org.Backend.ticketing_system.repositary.UserRepositary;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepositary userRepositary;

    public UserService(UserRepositary userRepositary) {
        this.userRepositary = userRepositary;
    }

    public User Usersignup(String username, String email, String password) {
        User user = new User();
        user.setName(username);
        user.setEmail(email);
        user.setPassword(password);
        return userRepositary.save(user);
    }
}
