package com.roomx.application.mapper;

import com.roomx.domain.model.entity.BookingEquipment;
import com.roomx.shared.dto.booking.response.BookingEquipmentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface BookingEquipmentAppMapper {
    BookingEquipmentResponse toResponse(BookingEquipment domain);

}
