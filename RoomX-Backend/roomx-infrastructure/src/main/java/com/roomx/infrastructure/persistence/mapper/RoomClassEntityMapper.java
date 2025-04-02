package com.roomx.infrastructure.persistence.mapper;

import com.roomx.domain.model.aggrerate.RoomClass;
import com.roomx.infrastructure.persistence.model.entity.RoomClassEntity;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface RoomClassEntityMapper {

    RoomClass toDomain(RoomClassEntity entity);

    RoomClassEntity toEntity(RoomClass domain);
}
