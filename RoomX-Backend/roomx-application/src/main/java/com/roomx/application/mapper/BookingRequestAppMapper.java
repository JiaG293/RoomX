package com.roomx.application.mapper;

import com.roomx.shared.dto.booking.request.BookingRequestCreateRequest;
import com.roomx.shared.dto.booking.response.BookingRequestResponse;
import com.roomx.domain.model.aggrerate.BookingRequest;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface BookingRequestAppMapper {
    BookingRequest toDomain(BookingRequestCreateRequest request);

    BookingRequestResponse toResponse(BookingRequest domain);
}
