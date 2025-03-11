package com.roomx.application.mapper;

import com.roomx.application.dto.model.UserApp;
import com.roomx.application.dto.user.request.UserCreateRequest;
import com.roomx.application.dto.user.response.UserResponse;
import com.roomx.application.dto.user.response.UserRoleResponse;
import com.roomx.domain.model.aggrerate.User;
import com.roomx.infrastructure.multitenancy.persistence.model.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",
        uses = RoleAppMapper.class,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserAppMapper {
    UserAppMapper INSTANCE = Mappers.getMapper(UserAppMapper.class);

    UserApp userAppToUserCreateRequest(UserCreateRequest request);

    UserCreateRequest userCreateRequestToUserApp(UserApp userApp);


    @Mapping(target = "userId", source = "id")
    @Mapping(target = "roles", source = "roles")
    UserRoleResponse toUserRoleResponse(User domain);

    @Mapping(target = "userId", source = "id")
    UserResponse toUserResponse(UserEntity entity);


}
