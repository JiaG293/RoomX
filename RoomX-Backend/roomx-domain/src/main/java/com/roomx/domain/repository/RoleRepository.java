package com.roomx.domain.repository;

import com.roomx.domain.model.aggrerate.Role;

import java.util.Optional;

public interface RoleRepository {
    Optional<Role> findById(String roleId);
    void save(Role role);
    void delete(Role role);
    void deleteById(String roleId);
    int findLevelByRole(String roleId);
}
