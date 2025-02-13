package com.roomx.domain.repository.user;

import com.roomx.domain.model.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    Optional<User> findById(UUID id);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    void save(User user);

    void delete(UUID id);


}
