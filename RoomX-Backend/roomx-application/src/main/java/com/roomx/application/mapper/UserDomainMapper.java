package com.roomx.application.mapper;

import com.roomx.application.dto.request.UserCreationRequest;
import com.roomx.application.dto.response.UserResponse;
import com.roomx.domain.model.UserDomain;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserDomainMapper {
    UserCreationRequest toUserCreationRequest(UserDomain userDomain);

    @Mappings({
            @Mapping(target = "maNhanVien", source = "username"),
            @Mapping(target = "email", source = "email"),
            @Mapping(target = "password", source = "password")
    })
    UserDomain toUserDomain(UserCreationRequest userCreationRequest);

    @Mappings({
            @Mapping(target = "maNhanVien", source = "maNhanVien"),
            @Mapping(target = "email", source = "email")
    })
    UserResponse toUserResponse(UserDomain userDomain);
}
