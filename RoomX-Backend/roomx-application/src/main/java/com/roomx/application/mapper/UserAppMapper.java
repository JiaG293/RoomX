package com.roomx.application.mapper;

import com.roomx.domain.model.aggrerate.Role;
import com.roomx.shared.dto.model.UserApp;
import com.roomx.shared.dto.user.request.UserCreateRequest;
import com.roomx.shared.dto.user.request.UserUpdateRequest;
import com.roomx.shared.dto.user.response.UserInfoReponse;
import com.roomx.shared.dto.user.response.UserResponse;
import com.roomx.shared.dto.user.response.UserRoleResponse;
import com.roomx.domain.model.aggrerate.Equipment;
import com.roomx.domain.model.aggrerate.User;
import com.roomx.infrastructure.persistence.model.entity.UserEntity;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.time.Instant;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {
                RoleAppMapper.class
        }
)

public interface UserAppMapper {

    UserApp userAppToUserCreateRequest(UserCreateRequest request);

    UserCreateRequest userCreateRequestToUserApp(UserApp userApp);


    @Mapping(target = "roles", source = "roles")
    UserRoleResponse toUserRoleResponse(User domain);

    UserResponse toUserResponse(UserEntity entity);


    UserResponse toResponse(User domain);

    @Mapping(target = "id", ignore = true)
    void updateDomainFromDto(UserUpdateRequest request, @MappingTarget User domain);

    @AfterMapping
    default void setUpdatedAt(@MappingTarget Equipment domain) {
        domain.setUpdatedAt(Instant.now());
    }

    @Mapping(target = "roles", source = "roles", qualifiedByName = "mapRoleSortByLevelToString")
    UserInfoReponse toResponseInfo(User userDomain);

    @Named("mapRoleSortByLevelToString")
    default List<String> mapRoleSortByLevelToString(Set<Role> roles) {
        if (roles == null) return Collections.emptyList();
        return roles.stream()
                .sorted(Comparator.comparing(Role::getLevel).reversed())
                .map(Role::getId)
                .toList();
    }
}
