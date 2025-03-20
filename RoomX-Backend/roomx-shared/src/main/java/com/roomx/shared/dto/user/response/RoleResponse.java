package com.roomx.shared.dto.user.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RoleResponse {
    private String roleName;
    private String description;
    private int level;
}
