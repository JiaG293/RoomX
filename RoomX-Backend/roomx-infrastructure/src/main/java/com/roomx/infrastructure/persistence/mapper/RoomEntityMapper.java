package com.roomx.infrastructure.persistence.mapper;

import com.roomx.domain.model.aggrerate.Room;
import com.roomx.infrastructure.persistence.model.entity.RoomEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface RoomEntityMapper {

    Room toDomain(RoomEntity entity);

    RoomEntity toEntity(Room domain);

}
