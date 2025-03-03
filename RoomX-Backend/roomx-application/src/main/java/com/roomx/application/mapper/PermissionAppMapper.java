package com.roomx.application.mapper;

import com.roomx.application.dto.user.request.PermissonCreateRequest;
import com.roomx.application.dto.user.response.PermissionReponse;
import com.roomx.domain.model.entity.Permission;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PermissionAppMapper {
    PermissionAppMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(PermissionAppMapper.class);

    @Mappings({
            @Mapping(target = "id", source = "permissionName"),
    })
    Permission toDomain(PermissonCreateRequest request);

    @Mappings({
            @Mapping(target = "permissionName", source = "id"),
    })
    PermissionReponse toResponse(Permission domain);

}
