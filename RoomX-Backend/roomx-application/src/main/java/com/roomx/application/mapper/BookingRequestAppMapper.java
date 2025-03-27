package com.roomx.application.mapper;

import com.roomx.domain.model.aggrerate.Equipment;
import com.roomx.shared.dto.booking.request.BookingRequestCreateRequest;
import com.roomx.shared.dto.booking.request.BookingRequestUserCreateRequest;
import com.roomx.shared.dto.booking.response.BookingRequestResponse;
import com.roomx.domain.model.aggrerate.BookingRequest;
import org.mapstruct.*;

import java.time.Instant;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {
                EquipmentRequestAppMapper.class,
                ServiceRequestAppMapper.class
        }
)
public interface BookingRequestAppMapper {

    @Mapping(target = "services", ignore = true)
    @Mapping(target = "equipments", ignore = true)
    BookingRequest toDomainUser(BookingRequestUserCreateRequest request);

    BookingRequest toDomain(BookingRequestCreateRequest request);

    BookingRequestResponse toResponse(BookingRequest domain);

    @AfterMapping
    default void setUpdatedAt(@MappingTarget BookingRequest domain) {
        domain.setUpdatedAt(Instant.now());
    }
}
