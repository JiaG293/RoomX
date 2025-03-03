package com.roomx.application.dto.user.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RolePermissonCreateReponse {
    private String roleName;
    private String description;
    private Set<RolePermissonCreateReponse> permissons;
}
