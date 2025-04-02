package com.roomx.infrastructure.persistence.mapper;


import com.roomx.domain.model.aggrerate.User;
import com.roomx.infrastructure.persistence.model.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
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
