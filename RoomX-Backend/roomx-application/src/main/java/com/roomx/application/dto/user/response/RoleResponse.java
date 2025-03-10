package com.roomx.application.dto.user.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RoleResponse {
    private String roleName;
    private String description;
    private int level;
}
