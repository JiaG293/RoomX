package com.roomx.infrastructure.multitenancy.persistence.mapper;

import com.roomx.domain.model.vo.EquipmentRequestId;
import com.roomx.infrastructure.multitenancy.persistence.model.ids.EquipmentRequestEntityId;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface EquipmentRequestEntityIdMapper {
    EquipmentRequestId toDomain(EquipmentRequestEntityId entity);
    EquipmentRequestEntityId toEntity(EquipmentRequestId domain);
}
