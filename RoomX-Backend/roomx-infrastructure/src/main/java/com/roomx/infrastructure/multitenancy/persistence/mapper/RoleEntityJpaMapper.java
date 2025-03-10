package com.roomx.infrastructure.multitenancy.persistence.mapper;

import com.roomx.domain.model.aggrerate.Role;
import com.roomx.infrastructure.multitenancy.persistence.model.entity.RoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface RoleEntityJpaMapper {

    @Mappings({
            @Mapping(target = "roleId", source = "id")
    })
    RoleEntity toEntity(Role domain);

    @Mappings({
            @Mapping(target = "id", source = "roleId")
    })
    Role toDomain(RoleEntity entity);
}
