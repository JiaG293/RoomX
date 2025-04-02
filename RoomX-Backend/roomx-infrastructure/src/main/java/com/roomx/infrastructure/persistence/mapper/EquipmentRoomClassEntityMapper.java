package com.roomx.infrastructure.persistence.mapper;

import com.roomx.domain.model.entity.EquipmentRoomClass;
import com.roomx.infrastructure.persistence.model.entity.EquipmentRoomClassEntity;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {
                EquipmentEntityMapper.class,
                RoomClassEntityMapper.class,
                EquipmentRoomClassEntityIdMapper.class
        }
)
public interface EquipmentRoomClassEntityMapper {

    EquipmentRoomClass toDomain(EquipmentRoomClassEntity entity);

    EquipmentRoomClassEntity toEntity(EquipmentRoomClass domain);
}
