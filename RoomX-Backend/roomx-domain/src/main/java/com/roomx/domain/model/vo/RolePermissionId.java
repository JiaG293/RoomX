package com.roomx.domain.model.vo;

import java.util.Objects;

public record RolePermissionId(String roleId, String permissionId) {
    public RolePermissionId(String roleId, String permissionId){
        this.roleId = Objects.requireNonNull(roleId, "ID vai trò không được null");
        this.permissionId = Objects.requireNonNull(permissionId, "ID quyền không được null");
    }
}
