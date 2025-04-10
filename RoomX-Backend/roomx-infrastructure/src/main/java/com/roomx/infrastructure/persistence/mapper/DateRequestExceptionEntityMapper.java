package com.roomx.infrastructure.persistence.mapper;

import com.roomx.domain.model.entity.DateRequestException;
import com.roomx.infrastructure.persistence.model.entity.DateRequestExceptionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.UUID;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface DateRequestExceptionEntityMapper {

    @Mapping(target = "bookingRequestId", source = "bookingRequest.id")
    DateRequestException toDomain(DateRequestExceptionEntity entity);

    @Mapping(target = "bookingRequest", expression = "java(new BookingRequestEntity(domain.getBookingRequestId()))")
    DateRequestExceptionEntity toEntity(DateRequestException domain);
}
