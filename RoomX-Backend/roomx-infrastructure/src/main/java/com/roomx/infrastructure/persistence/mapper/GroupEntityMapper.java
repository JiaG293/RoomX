package com.roomx.infrastructure.persistence.mapper;

import com.roomx.domain.model.aggrerate.Group;
import com.roomx.infrastructure.persistence.model.entity.GroupEntity;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface GroupEntityMapper {

    Group toDomain(GroupEntity entity);

    GroupEntity toEntity(Group domain);
}
