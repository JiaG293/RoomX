package com.roomx.domain.model.entity;

import com.roomx.domain.model.aggrerate.Role;
import com.roomx.domain.model.vo.RolePermissionId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class RolePermission {
    private RolePermissionId id;
    private Role role;
    private Permission permission;
}
