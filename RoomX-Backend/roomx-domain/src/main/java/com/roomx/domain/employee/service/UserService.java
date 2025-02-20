package com.roomx.domain.employee.service;

import com.roomx.domain.employee.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> getUserById(String id);

    Optional<User> getUserByUsername(String username);

    Optional<User> getUserByEmail(String email);

    List<User> getAllUsers();

    void createUser(User user);

    void updateUser(User user);

    void deactivateUser(String id);

}
