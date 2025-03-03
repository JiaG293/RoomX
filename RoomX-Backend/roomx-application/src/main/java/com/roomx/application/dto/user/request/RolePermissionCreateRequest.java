package com.roomx.application.dto.user.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RolePermissionCreateRequest {
    private String roleName;
    private String description;
    private List<PermissonCreateRequest> permissions;
}
