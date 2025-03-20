package com.roomx.shared.dto.user.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleResponse {
    private String userId;
    private String userCode;
    private List<RoleResponse> roles;
}
