package com.roomx.infrastructure.multitenancy.persistence.model.ids;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Embeddable
public class UserRoleEntityId implements Serializable {
    public static final String COLUMN_USERID_NAME = "user_id";
    public static final String COLUMN_ROLEID_NAME = "role_id";
    private static final long serialVersionUID = -4319518906904968896L;

    @NotNull
    @Column(name = COLUMN_USERID_NAME, nullable = false)
    private UUID userId;

    @Size(max = 64)
    @NotNull
    @Column(name = COLUMN_ROLEID_NAME, nullable = false, length = 64)
    private String roleId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserRoleEntityId entity = (UserRoleEntityId) o;
        return Objects.equals(this.roleId, entity.roleId) &&
                Objects.equals(this.userId, entity.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleId, userId);
    }

}