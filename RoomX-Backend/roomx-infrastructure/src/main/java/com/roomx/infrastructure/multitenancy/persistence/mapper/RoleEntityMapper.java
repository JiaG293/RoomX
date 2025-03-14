package com.roomx.infrastructure.multitenancy.persistence.mapper;

import com.roomx.domain.model.aggrerate.Role;
import com.roomx.infrastructure.multitenancy.persistence.model.entity.RoleEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface RoleEntityMapper {

    @Mappings({
            @Mapping(target = "roleId", source = "id")
    })
    RoleEntity toEntity(Role domain);

    @Mappings({
            @Mapping(target = "id", source = "roleId")
    })
    Role toDomain(RoleEntity entity);
}
