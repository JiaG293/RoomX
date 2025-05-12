package com.roomx.infrastructure.persistence.model.projection;

import java.util.UUID;

public interface GroupProjection {
    UUID getId();
    String getName();
    String getGroupType();
    String getGroupCode();
    String getStatus();

    UUID getBranchId();
    String getBranchName();
    String getBranchCode();

    UUID getCreatedBy();
    String getOwnerEmail();
    String getOwnerUserCode();
    String getOwnerFirstName();
    String getOwnerLastName();

    Integer getQuantityMember();

}
