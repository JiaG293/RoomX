package com.roomx.application.mapper;

import com.roomx.application.dto.model.UserApp;
import com.roomx.application.dto.user.request.UserCreateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserAppMapper {
    UserAppMapper INSTANCE = Mappers.getMapper(UserAppMapper.class);

    UserApp userAppToUserCreateRequest(UserCreateRequest request);

    UserCreateRequest userCreateRequestToUserApp(UserApp userApp);

}
