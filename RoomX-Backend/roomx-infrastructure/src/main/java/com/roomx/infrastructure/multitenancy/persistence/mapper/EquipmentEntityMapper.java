package com.roomx.infrastructure.multitenancy.persistence.mapper;

import com.roomx.domain.model.aggrerate.Equipment;
import com.roomx.infrastructure.multitenancy.persistence.model.entity.EquipmentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface EquipmentEntityMapper {

    Equipment toDomain(EquipmentEntity entity);

    EquipmentEntity toEntity(Equipment domain);
}
