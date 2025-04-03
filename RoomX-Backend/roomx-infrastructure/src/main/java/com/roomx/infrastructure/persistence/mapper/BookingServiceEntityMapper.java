package com.roomx.infrastructure.persistence.mapper;

import com.roomx.domain.model.entity.BookingService;
import com.roomx.infrastructure.persistence.model.entity.BookingServiceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {
                ServiceEntityMapper.class,
                BookingEntityMapper.class,
                BookingServiceEntityIdMapper.class
        }
)
public interface BookingServiceEntityMapper {
    BookingService toDomain(BookingServiceEntity entity);

    BookingServiceEntity toEntity(BookingService domain);
}
