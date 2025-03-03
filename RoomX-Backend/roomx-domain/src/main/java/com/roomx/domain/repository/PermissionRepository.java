package com.roomx.domain.repository;

import com.roomx.domain.model.entity.Permission;

import java.util.Optional;

public interface PermissionRepository {
    Optional<Permission> findById(String id);
    void save(Permission permission);
    void delete(Permission permission);
    void deleteById(String id);
}
