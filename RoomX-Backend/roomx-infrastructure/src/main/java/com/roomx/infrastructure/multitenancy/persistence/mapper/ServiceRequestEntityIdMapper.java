package com.roomx.infrastructure.multitenancy.persistence.mapper;

import com.roomx.domain.model.vo.ServiceRequestId;
import com.roomx.infrastructure.multitenancy.persistence.model.ids.ServiceRequestEntityId;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface ServiceRequestEntityIdMapper {
    ServiceRequestId toDomain(ServiceRequestEntityId entity);
    ServiceRequestEntityId toEntity(ServiceRequestId domain);
}
