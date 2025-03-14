package com.roomx.infrastructure.multitenancy.persistence.mapper;

import com.roomx.domain.model.aggrerate.Service;
import com.roomx.infrastructure.multitenancy.persistence.model.entity.ServiceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface ServiceEntityJpaMapper {

    @Mapping(target = "id", source = "id")
    Service toDomain(ServiceEntity entity);

    @Mapping(target = "id", source = "id")
    ServiceEntity toEntity(Service domain);
}
