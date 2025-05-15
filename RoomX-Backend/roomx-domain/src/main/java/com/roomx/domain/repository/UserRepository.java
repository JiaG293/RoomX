package com.roomx.domain.repository;

import com.roomx.domain.model.aggrerate.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    Optional<User> findById(UUID id, boolean enabled);

    Optional<User> findByIdAll(String id);

    Optional<User> findByUserCode(String userCode);

    Optional<User> findByEmail(String email);

    List<User> findAll();

    void save(User user);

    void delete(String id);


    List<User> findAllUserWithRole(String roleName);

    Optional<User> findByEmail(String email, boolean enabled);

    Optional<User> findByEmailAndStatus(String email, boolean enabled);

    Optional<UUID> findByEmailCustom(String email);

    void deleteUserRole(String userId);

    boolean checkEmailExist(String email);
}
