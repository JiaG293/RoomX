package com.roomx.infrastructure.persistence.model.projection;

import java.util.UUID;

public interface PlaceBranchProjection {
    UUID getId();
    UUID getGroupId();
    String getName();
    String getGroupType();
    UUID getCreateBy();
    String getGroupCode();
    String getStatus();
    UUID getMemberId();
}
