package com.roomx.infrastructure.persistence.mapper;

import com.roomx.domain.model.vo.BookingServiceId;
import com.roomx.infrastructure.persistence.model.entity.BookingServiceEntity;
import com.roomx.infrastructure.persistence.model.ids.BookingServiceEntityId;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface BookingServiceEntityIdMapper {
    BookingServiceId toDomain(BookingServiceEntityId entity);
    BookingServiceEntityId toEntity(BookingServiceId domain);
}
