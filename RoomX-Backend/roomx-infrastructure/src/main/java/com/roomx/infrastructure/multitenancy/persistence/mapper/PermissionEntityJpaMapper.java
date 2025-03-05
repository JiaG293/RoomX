package com.roomx.infrastructure.multitenancy.persistence.mapper;

import com.roomx.domain.model.entity.Permission;
import com.roomx.infrastructure.multitenancy.persistence.model.entity.PermissonEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PermissionEntityJpaMapper {

    @Mappings({
            @Mapping(target = "id", source = "permissionId")
    })
    Permission toDomain(PermissonEntity entity);

    @Mappings({
            @Mapping(target = "permissionId", source = "id"),
    })
    PermissonEntity toEntity(Permission domain);
}
