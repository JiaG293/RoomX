package com.roomx.infrastructure.multitenancy.keycloak.mapper;

import com.roomx.domain.employee.model.User;
import org.keycloak.representations.idm.UserRepresentation;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserRepresentationMapper {

    UserRepresentationMapper INSTANCE = Mappers.getMapper(UserRepresentationMapper.class);

    UserRepresentation toEntity(User user);


   /* @Mappings({
            @Mapping(target = "username", source = "username"),
            @Mapping(target = "email", source = "email")
    })*/
    User toDomain(UserRepresentation userRepresentation);
}
