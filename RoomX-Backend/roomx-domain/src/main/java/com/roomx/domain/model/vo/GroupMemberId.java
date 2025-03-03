package com.roomx.domain.model.vo;

import java.util.Objects;
import java.util.UUID;

public record GroupMemberId(UUID userId, UUID groupId) {
    public GroupMemberId(UUID userId, UUID groupId) {
        this.userId = Objects.requireNonNull(userId, "ID người dùng không được null");
        this.groupId = Objects.requireNonNull(groupId, "ID nhóm không được null");
    }
}
