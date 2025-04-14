package com.roomx.infrastructure.persistence.mapper;


import com.roomx.domain.model.aggrerate.User;
import com.roomx.infrastructure.persistence.model.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {
                RoleEntityMapper.class
        }

)
public interface UserEntityMapper {



    User toDomain(UserEntity entity);


    UserEntity toEntity(User domain);

}
