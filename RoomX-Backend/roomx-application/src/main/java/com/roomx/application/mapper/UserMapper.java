package com.roomx.application.mapper;

import com.roomx.application.dto.user.request.UserCreateRequest;
import com.roomx.domain.model.aggrerate.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    @Mapping(target = "userType", source = "type")
    User userCreateRequestToDomain(UserCreateRequest request);
}
