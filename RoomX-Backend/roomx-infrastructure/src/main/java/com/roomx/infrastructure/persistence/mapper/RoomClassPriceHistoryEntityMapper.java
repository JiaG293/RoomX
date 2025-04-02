package com.roomx.infrastructure.persistence.mapper;

import com.roomx.domain.model.entity.RoomClassPriceHistory;
import com.roomx.infrastructure.persistence.model.entity.RoomClassPriceHistoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface RoomClassPriceHistoryEntityMapper {
    RoomClassPriceHistory toDomain(RoomClassPriceHistoryEntity entity);

    RoomClassPriceHistoryEntity toEntity(RoomClassPriceHistory domain);
}
