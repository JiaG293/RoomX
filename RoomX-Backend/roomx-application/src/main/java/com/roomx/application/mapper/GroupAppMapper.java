package com.roomx.application.mapper;

import com.roomx.infrastructure.persistence.model.projection.GroupProjection;
import com.roomx.shared.dto.resource.response.GroupFilterResponse;
import com.roomx.shared.dto.user.request.GroupCreateAdminRequest;
import com.roomx.shared.dto.user.request.GroupCreateRequest;
import com.roomx.shared.dto.user.request.GroupCreateUserRequest;
import com.roomx.shared.dto.user.response.GroupResponse;
import com.roomx.domain.model.aggrerate.Group;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {
                UserAppMapper.class,
                GroupMemberAppMapper.class
        }
)
public interface GroupAppMapper {

    @Mapping(target = "groupMembers", ignore = true)
    Group toDomainAdmin(GroupCreateAdminRequest request);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "branchId", source = "branch.id")
    GroupResponse toResponse(Group domain);

    GroupCreateRequest adminToGroupRequest(GroupCreateAdminRequest request);
    GroupCreateRequest userToGroupRequest(GroupCreateUserRequest request);


    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    GroupFilterResponse toResponseFilter(GroupProjection groupProjection);
}
