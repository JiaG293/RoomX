package com.roomx.infrastructure.persistence.mapper;

import com.roomx.domain.model.entity.EquipmentPriceHistory;
import com.roomx.infrastructure.persistence.model.entity.EquipmentPriceHistoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface EquipmentPriceHistoryEntityMapper {
    EquipmentPriceHistory toDomain(EquipmentPriceHistoryEntity entity);

    EquipmentPriceHistoryEntity toEntity(EquipmentPriceHistory domain);
}
