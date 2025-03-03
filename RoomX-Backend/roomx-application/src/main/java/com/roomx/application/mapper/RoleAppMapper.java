package com.roomx.application.mapper;

import com.roomx.application.dto.user.request.RoleCreateRequest;
import com.roomx.application.dto.user.response.RoleResponse;
import com.roomx.domain.model.aggrerate.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = PermissionAppMapper.class, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface RoleAppMapper {
    RoleAppMapper INSTANCE = Mappers.getMapper(RoleAppMapper.class);

    @Mappings({
            @Mapping(target = "id", source = "roleName"),
    })
    Role toDomain(RoleCreateRequest request);

    @Mappings({
            @Mapping(target = "roleName", source = "id"),
    })
    RoleCreateRequest toApp(Role role);

    @Mappings({
            @Mapping(target = "roleName", source = "id"),
            @Mapping(target = "permissions", source = "permissions")
    })
    RoleResponse toResponse(Role domain);

}
