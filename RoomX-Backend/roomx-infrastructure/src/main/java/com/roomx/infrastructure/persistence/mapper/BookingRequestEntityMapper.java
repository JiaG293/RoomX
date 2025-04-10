package com.roomx.infrastructure.persistence.mapper;

import com.roomx.domain.model.aggrerate.BookingRequest;
import com.roomx.infrastructure.persistence.model.entity.BookingRequestEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {
            DateRequestExceptionEntityMapper.class
        }
)
public interface BookingRequestEntityMapper {
    BookingRequest toDomain(BookingRequestEntity entity);
    BookingRequestEntity toEntity(BookingRequest domain);


}
