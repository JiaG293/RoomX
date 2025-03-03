package com.roomx.domain.model.aggrerate;

import com.roomx.domain.model.entity.Permission;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class Role {
    private String id;
    private String description;
    @Builder.Default
    private Set<Permission> permissions = new HashSet<>();

    public boolean hasPermission(Permission permission) {
        return permissions.contains(permission);
    }

    public void addPermission(Permission permission) {
        if (permission == null) {
            throw new IllegalArgumentException("Permission cannot be null");
        }
        if (permissions.contains(permission)) {
            throw new IllegalStateException("Permission already assigned");
        }
        permissions.add(permission);
    }

    public void removePermission(Permission permission) {
        if (permission == null) {
            throw new IllegalArgumentException("Permission cannot be null");
        }
        permissions.remove(permission);
    }


}
