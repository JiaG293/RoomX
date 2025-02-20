package com.roomx.infrastructure.multitenancy.persistence.mapper;

import com.roomx.domain.employee.model.User;
import com.roomx.infrastructure.multitenancy.persistence.model.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserEntityJpaMapper {

    UserEntityJpaMapper INSTANCE = Mappers.getMapper(UserEntityJpaMapper.class);

    /*@Mappings({
            @Mapping(target = "email", source = "email")
    })*/
    User toDomain(UserEntity entity);

    UserEntity toEntity(User domain);

}
