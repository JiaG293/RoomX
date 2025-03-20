package com.roomx.application.mapper;

import com.roomx.shared.dto.booking.request.EquipmentBookingRequest;
import com.roomx.domain.model.entity.EquipmentRequest;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface EquipmentRequestAppMapper {
    EquipmentRequest toDomain(EquipmentBookingRequest request);

}
