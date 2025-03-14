package com.roomx.infrastructure.multitenancy.persistence.mapper;


import com.roomx.domain.model.aggrerate.User;
import com.roomx.infrastructure.multitenancy.persistence.model.entity.UserEntity;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",
        uses = RoleEntityMapper.class,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserEntityMapper {

    UserEntityMapper INSTANCE = Mappers.getMapper(UserEntityMapper.class);

    @Mapping(target = "roles", source = "roles")
    User toDomain(UserEntity entity);

    @Mapping(target = "roles", source = "roles")
    UserEntity toEntity(User domain);

}
