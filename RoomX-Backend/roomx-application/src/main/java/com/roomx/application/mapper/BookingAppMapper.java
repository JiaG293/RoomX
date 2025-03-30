package com.roomx.application.mapper;

import com.roomx.domain.model.aggrerate.Booking;
import com.roomx.domain.model.aggrerate.BookingRequest;
import com.roomx.shared.dto.booking.response.BookingResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface BookingAppMapper {

    @Mapping(target = "participants", ignore = true)
    Booking toDomain(BookingRequest request);

    @Mapping(target = "bookingRequestId", source = "bookingRequest.id")
    @Mapping(target = "roomId", source = "room.id")
    @Mapping(target = "previousRoomId", source = "previousRoom.id")
    BookingResponse toResponse(Booking domain);
}
