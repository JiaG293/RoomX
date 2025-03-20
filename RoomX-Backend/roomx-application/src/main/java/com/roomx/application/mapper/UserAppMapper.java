package com.roomx.application.mapper;

import com.roomx.shared.dto.model.UserApp;
import com.roomx.shared.dto.user.request.UserCreateRequest;
import com.roomx.shared.dto.user.request.UserUpdateRequest;
import com.roomx.shared.dto.user.response.UserResponse;
import com.roomx.shared.dto.user.response.UserRoleResponse;
import com.roomx.domain.model.aggrerate.Equipment;
import com.roomx.domain.model.aggrerate.User;
import com.roomx.infrastructure.multitenancy.persistence.model.entity.UserEntity;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.time.Instant;

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

    @Mapping(target = "id", ignore = true)
    void updateDomainFromDto(UserUpdateRequest request, @MappingTarget User domain);

    @AfterMapping
    default void setUpdatedAt(@MappingTarget Equipment domain) {
        domain.setUpdatedAt(Instant.now());
    }

}
