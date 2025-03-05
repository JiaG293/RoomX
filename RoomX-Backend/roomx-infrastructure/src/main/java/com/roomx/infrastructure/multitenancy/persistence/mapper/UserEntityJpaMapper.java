package com.roomx.infrastructure.multitenancy.persistence.mapper;


import com.roomx.domain.model.aggrerate.User;
import com.roomx.infrastructure.multitenancy.persistence.model.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserEntityJpaMapper {

    UserEntityJpaMapper INSTANCE = Mappers.getMapper(UserEntityJpaMapper.class);

    @Mappings({
            @Mapping(target = "roles", ignore = true)
    })
    User toDomain(UserEntity entity);

    UserEntity toEntity(User domain);

}
