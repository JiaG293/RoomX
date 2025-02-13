package com.roomx.infrastructure.keycloak.mapper;

import com.roomx.domain.model.UserDomain;
import org.keycloak.representations.idm.UserRepresentation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserRepresentationMapper {

    UserRepresentation toUserRepresentation(UserDomain userDomain);


    @Mappings({
            @Mapping(target = "maNhanVien", source = "username"),
            @Mapping(target = "email", source = "email")
    })
    UserDomain toUserDomain(UserRepresentation userRepresentation);
}
