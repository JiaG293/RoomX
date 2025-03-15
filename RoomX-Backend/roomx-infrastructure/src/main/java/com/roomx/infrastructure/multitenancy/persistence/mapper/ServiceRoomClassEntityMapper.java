package com.roomx.infrastructure.multitenancy.persistence.mapper;

import com.roomx.domain.model.entity.ServiceRoomClass;
import com.roomx.infrastructure.multitenancy.persistence.model.entity.ServiceRoomClassEntity;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {
                ServiceEntityMapper.class,
                RoomClassEntityMapper.class,
                ServiceRoomClassEntityIdMapper.class
        }
)
public interface ServiceRoomClassEntityMapper {

    ServiceRoomClassEntity toEntity(ServiceRoomClass domain);

    ServiceRoomClass toDomain(ServiceRoomClassEntity entity);

}
