package com.roomx.infrastructure.multitenancy.persistence.mapper;

import com.roomx.domain.model.vo.EquipmentRoomClassId;
import com.roomx.infrastructure.multitenancy.persistence.model.ids.EquipmentRoomClassEntityId;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface EquipmentRoomClassEntityIdMapper {
    EquipmentRoomClassId toDomain(EquipmentRoomClassEntityId entityId);
    EquipmentRoomClassEntityId toEntity(EquipmentRoomClassId domainId);
}
