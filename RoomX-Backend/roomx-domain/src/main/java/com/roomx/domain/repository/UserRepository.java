package com.roomx.domain.repository;

import com.roomx.domain.model.aggrerate.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    Optional<User> findById(UUID id);

    Optional<User> findByUserCode(String userCode);

    Optional<User> findByEmail(String email);

    List<User> findAll();

    void save(User user);

    void delete(String id);


}
