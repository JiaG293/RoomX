package com.roomx.infrastructure.persistence.mapper;

import com.roomx.domain.model.aggrerate.Equipment;
import com.roomx.domain.model.entity.EquipmentPriceHistory;
import com.roomx.infrastructure.persistence.model.entity.EquipmentEntity;
import com.roomx.infrastructure.persistence.model.entity.EquipmentPriceHistoryEntity;
import org.mapstruct.*;

import java.util.Comparator;
import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {
                EquipmentPriceHistoryEntityMapper.class
        }
)
public interface EquipmentEntityMapper {

    Equipment toDomain(EquipmentEntity entity);

    EquipmentEntity toEntity(Equipment domain);


    @Named("toDomainSpec")
    @Mapping(target = "price", expression = "java(getLatestActivePrice(entity.getPriceHistories()))")
    Equipment toDomainSpec(EquipmentEntity entity);

    default EquipmentPriceHistory getLatestActivePrice(List<EquipmentPriceHistoryEntity> priceHistories) {
        return priceHistories.stream()
                .filter(equipmentPrice -> equipmentPrice.getValidEnd() == null)
                .max(Comparator.comparing(EquipmentPriceHistoryEntity::getValidFrom))
                .map(equipmentPrice -> EquipmentPriceHistory.builder()
                        .id(equipmentPrice.getId())
                        .unitPrice(equipmentPrice.getUnitPrice())
                        .validEnd(equipmentPrice.getValidEnd())
                        .validFrom(equipmentPrice.getValidFrom())
                        .build())
                .orElse(null);
    }
}
