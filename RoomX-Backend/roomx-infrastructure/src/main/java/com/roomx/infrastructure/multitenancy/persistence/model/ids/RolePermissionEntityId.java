package com.roomx.infrastructure.multitenancy.persistence.model.ids;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Embeddable
public class RolePermissionEntityId implements Serializable {
    public static final String COLUMN_ROLEID_NAME = "role_id";
    public static final String COLUMN_PERMISSIONID_NAME = "permission_id";
    private static final long serialVersionUID = 40671516421291619L;

    @Size(max = 64)
    @NotNull
    @Column(name = COLUMN_ROLEID_NAME, nullable = false, length = 64)
    private String roleId;

    @Size(max = 64)
    @NotNull
    @Column(name = COLUMN_PERMISSIONID_NAME, nullable = false, length = 64)
    private String permissionId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        RolePermissionEntityId entity = (RolePermissionEntityId) o;
        return Objects.equals(this.permissionId, entity.permissionId) &&
                Objects.equals(this.roleId, entity.roleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(permissionId, roleId);
    }

}