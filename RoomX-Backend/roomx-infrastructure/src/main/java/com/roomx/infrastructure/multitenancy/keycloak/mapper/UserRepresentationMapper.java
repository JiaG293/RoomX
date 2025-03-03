package com.roomx.infrastructure.multitenancy.keycloak.mapper;


import com.roomx.domain.model.aggrerate.User;
import org.keycloak.representations.idm.UserRepresentation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserRepresentationMapper {

    UserRepresentationMapper INSTANCE = Mappers.getMapper(UserRepresentationMapper.class);

    UserRepresentation toEntity(User user1);


    User toDomain(UserRepresentation userRepresentation);
}
