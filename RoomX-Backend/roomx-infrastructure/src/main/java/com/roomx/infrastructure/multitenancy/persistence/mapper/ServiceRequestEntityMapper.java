package com.roomx.infrastructure.multitenancy.persistence.mapper;

import com.roomx.domain.model.entity.ServiceRequest;
import com.roomx.infrastructure.multitenancy.persistence.model.entity.ServiceRequestEntity;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {
                ServiceRequestEntityIdMapper.class,
                BookingRequestEntityMapper.class,
                ServiceEntityMapper.class
        }
)
public interface ServiceRequestEntityMapper {
    ServiceRequestEntity toEntity(ServiceRequest domain);

    ServiceRequest toDomain(ServiceRequestEntity entity);
}
