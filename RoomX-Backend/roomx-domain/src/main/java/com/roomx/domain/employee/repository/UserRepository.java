package com.roomx.domain.employee.repository;

import com.roomx.domain.employee.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    Optional<User> findById(String id);

    Optional<User> findByEmployeeId(String employeeId);

    Optional<User> findByEmail(String email);

    List<User> findAll();

    void save(User user);

    void delete(String id);

}
