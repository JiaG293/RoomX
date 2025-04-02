package com.roomx.infrastructure.persistence.model.ids;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
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
public class GroupMemberEntityId implements Serializable {
    public static final String COLUMN_USERID_NAME = "user_id";
    public static final String COLUMN_GROUPID_NAME = "group_id";
    private static final long serialVersionUID = -1241215963217673150L;

    @NotNull
    @Column(name = COLUMN_USERID_NAME, nullable = false)
    private UUID userId;

    @NotNull
    @Column(name = COLUMN_GROUPID_NAME, nullable = false)
    private UUID groupId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        GroupMemberEntityId entity = (GroupMemberEntityId) o;
        return Objects.equals(this.groupId, entity.groupId) &&
                Objects.equals(this.userId, entity.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupId, userId);
    }

}