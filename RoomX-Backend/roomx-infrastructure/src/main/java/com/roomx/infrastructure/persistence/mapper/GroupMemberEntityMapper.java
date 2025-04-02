package com.roomx.infrastructure.persistence.mapper;

import com.roomx.domain.model.entity.GroupMember;
import com.roomx.infrastructure.persistence.model.entity.GroupMemberEntity;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {
                GroupEntityMapper.class,
                UserEntityMapper.class,
                GroupMemberEntityIdMapper.class
        }
)
public interface GroupMemberEntityMapper {
    GroupMember toDomain(GroupMemberEntity entity);
    GroupMemberEntity toEntity(GroupMember domain);
}
