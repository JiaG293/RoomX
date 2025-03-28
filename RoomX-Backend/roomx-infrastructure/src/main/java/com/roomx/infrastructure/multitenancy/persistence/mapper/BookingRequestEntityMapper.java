package com.roomx.infrastructure.multitenancy.persistence.mapper;

import com.roomx.domain.model.aggrerate.BookingRequest;
import com.roomx.domain.model.aggrerate.Equipment;
import com.roomx.infrastructure.multitenancy.persistence.model.entity.BookingRequestEntity;
import org.mapstruct.*;

import java.time.Instant;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface BookingRequestEntityMapper {
    BookingRequest toDomain(BookingRequestEntity entity);
    BookingRequestEntity toEntity(BookingRequest domain);


}
