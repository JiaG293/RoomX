package com.roomx.infrastructure.multitenancy.persistence.mapper;

import com.roomx.domain.model.vo.ServiceRoomClassId;
import com.roomx.infrastructure.multitenancy.persistence.model.ids.ServiceRoomClassEntityId;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface ServiceRoomClassEntityIdMapper {

    ServiceRoomClassId toDomain(ServiceRoomClassEntityId entity);

    ServiceRoomClassEntityId toEntity(ServiceRoomClassId domain);

}
