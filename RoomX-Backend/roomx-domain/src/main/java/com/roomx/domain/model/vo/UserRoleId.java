package com.roomx.domain.model.vo;

import java.util.Objects;
import java.util.UUID;

public record UserRoleId(UUID userId, String roleId) {
    public UserRoleId(UUID userId, String roleId) {
        this.userId = Objects.requireNonNull(userId, "ID người dùng không được null");
        this.roleId = Objects.requireNonNull(roleId, "ID vai trò không được null");
    }
}
