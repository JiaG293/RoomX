package com.roomx.domain.repository;

import com.roomx.domain.model.entity.RolePermission;
import com.roomx.domain.model.vo.RolePermissionId;

import java.util.Optional;

public interface RolePermissionRepository {
    Optional<RolePermission> findById(RolePermissionId id);

}
