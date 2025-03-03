package com.roomx.infrastructure.multitenancy.persistence.model.entity;

import com.roomx.infrastructure.multitenancy.persistence.model.ids.RolePermissionEntityId;
import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = RolePermissionEntity.TABLE_NAME)
public class RolePermissionEntity {
    public static final String TABLE_NAME = "role_permission";

    @EmbeddedId
    private RolePermissionEntityId id;

    @MapsId("roleId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "role_id", nullable = false)
    private RoleEntity role;

    @MapsId("permissionId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "permission_id", nullable = false)
    private PermissonEntity permission;

}