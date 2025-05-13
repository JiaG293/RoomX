package com.roomx.infrastructure.persistence.model.projection;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.roomx.shared.dto.resource.base.EquipmentPriceDto;
import com.roomx.shared.dto.user.response.GroupMemberDetailResponse;

import java.util.Collections;
import java.util.List;
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

    String getJsonMembers();

    default List<GroupMemberDetailResponse> getMembers() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(getJsonMembers(), new TypeReference<>() {});
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

}
