package com.roomx.domain.repository;

import com.roomx.domain.model.UserDomain;

import java.util.Optional;

public interface UserDomainRepository {
    Optional<UserDomain> findById(String realm, String id);
    Optional<UserDomain> findByEmail(String realm, String email);
    void save(String realm, UserDomain userDomain);
    void updateUser(String realm, String id, UserDomain userDomain);
    void deleteById(String realm, String id);
}
