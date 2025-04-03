package com.roomx.application.mapper;

import com.roomx.domain.model.entity.BookingService;
import com.roomx.shared.dto.booking.response.BookingServiceResponse;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface BookingServiceAppMapper {

    BookingServiceResponse toResponse(BookingService domain);
}
