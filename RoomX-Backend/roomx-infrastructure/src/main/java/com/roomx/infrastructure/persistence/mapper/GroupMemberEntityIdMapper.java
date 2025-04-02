package com.roomx.infrastructure.persistence.mapper;

import com.roomx.domain.model.vo.GroupMemberId;
import com.roomx.infrastructure.persistence.model.ids.GroupMemberEntityId;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface GroupMemberEntityIdMapper {
    GroupMemberId toDomain(GroupMemberEntityId entity);
    GroupMemberEntityId toEntity(GroupMemberId domain);
}
