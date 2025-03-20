package com.roomx.application.mapper;

import com.roomx.domain.model.entity.GroupMember;
import com.roomx.shared.dto.user.request.GroupMemberRequest;
import com.roomx.shared.dto.user.response.GroupMemberResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {
                UserAppMapper.class
        }
)
public interface GroupMemberAppMapper {
    GroupMember toDomain(GroupMemberRequest request);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "userCode", source = "user.userCode")
    @Mapping(target = "lastName", source = "user.lastName")
    @Mapping(target = "firstName", source = "user.firstName")
    @Mapping(target = "groupId", source = "group.id")
    GroupMemberResponse toResponse(GroupMember domain);
}
