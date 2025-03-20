package com.roomx.application.mapper;

import com.roomx.shared.dto.booking.request.ServiceBookingRequest;
import com.roomx.domain.model.entity.ServiceRequest;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface ServiceRequestAppMapper {
    ServiceRequest toDomain(ServiceBookingRequest request);


}
