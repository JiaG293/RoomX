package com.roomx.application.mapper;

import com.roomx.shared.dto.user.request.RoleCreateRequest;
import com.roomx.shared.dto.user.response.RoleInfoResponse;
import com.roomx.domain.model.aggrerate.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface RoleAppMapper {

    @Mapping(target = "id", source = "roleName")
    Role toDomain(RoleCreateRequest request);

    @Mapping(target = "roleName", source = "id")
    RoleInfoResponse toResponse(Role domain);



}
