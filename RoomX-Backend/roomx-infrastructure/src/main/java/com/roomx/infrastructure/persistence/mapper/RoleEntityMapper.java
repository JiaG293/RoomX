package com.roomx.infrastructure.persistence.mapper;

import com.roomx.domain.model.aggrerate.Role;
import com.roomx.infrastructure.persistence.model.entity.RoleEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface RoleEntityMapper {

    @Mapping(target = "roleId", source = "id")
    RoleEntity toEntity(Role domain);

    @Mapping(target = "id", source = "roleId")
    Role toDomain(RoleEntity entity);
}
