package com.roomx.infrastructure.multitenancy.persistence.mapper;

import com.roomx.domain.model.aggrerate.BookingRequest;
import com.roomx.infrastructure.multitenancy.persistence.model.entity.BookingRequestEntity;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface BookingRequestEntityMapper {
    BookingRequest toDomain(BookingRequestEntity entity);

    BookingRequestEntity toEntity(BookingRequest domain);
}
