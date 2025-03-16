package com.roomx.infrastructure.multitenancy.persistence.mapper;

import com.roomx.domain.model.entity.EquipmentRequest;
import com.roomx.infrastructure.multitenancy.persistence.model.entity.BookingRequestEntity;
import com.roomx.infrastructure.multitenancy.persistence.model.entity.EquipmentRequestEntity;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {
                EquipmentEntityMapper.class,
                BookingRequestEntityMapper.class,
                EquipmentRequestIdMapper.class
        }
)
public interface EquipmentRequestEntityMapper {
    EquipmentRequest toDomain(EquipmentRequestEntity entity);

    EquipmentRequestEntity toEntity(EquipmentRequest domain);
}
