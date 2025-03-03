package com.roomx.application.mapper;

import com.roomx.application.dto.user.request.UserCreateRequest;
import com.roomx.domain.model.aggrerate.User;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {
    User UserCreateRequestToDomain(UserCreateRequest request);
}
