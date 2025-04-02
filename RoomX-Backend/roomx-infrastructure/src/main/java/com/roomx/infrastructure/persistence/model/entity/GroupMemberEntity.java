package com.roomx.infrastructure.persistence.model.entity;

import com.roomx.infrastructure.persistence.model.ids.GroupMemberEntityId;
import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = GroupMemberEntity.TABLE_NAME)
public class GroupMemberEntity {
    public static final String TABLE_NAME = "group_member";

    @EmbeddedId
    private GroupMemberEntityId id;

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @MapsId("groupId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "group_id", nullable = false)
    private GroupEntity group;

}