package com.roomx.infrastructure.persistence.service;

import com.roomx.domain.model.aggrerate.Group;
import com.roomx.infrastructure.persistence.dto.GroupFilter;
import com.roomx.infrastructure.persistence.model.projection.GroupProjection;
import com.roomx.shared.dto.resource.response.GroupFilterResponse;
import com.roomx.shared.dto.user.request.GroupCreateAdminRequest;
import com.roomx.shared.dto.user.request.GroupCreateRequest;
import com.roomx.shared.dto.user.response.GroupDetailResponse;
import com.roomx.shared.dto.user.response.GroupMemberDetailResponse;
import io.micrometer.observation.ObservationFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;


public interface GroupEntityService {
    Group createGroup(GroupCreateRequest request, String userId, String role);

    Page<GroupProjection> filterSearchGroup(GroupFilter groupFilter, Pageable pageable);

    Optional<GroupDetailResponse> getDetailGroup(String groupId, String status);
}
