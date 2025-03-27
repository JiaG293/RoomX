package com.roomx.application.mapper;

import com.roomx.shared.dto.booking.request.ServiceBookingRequest;
import com.roomx.domain.model.entity.ServiceRequest;
import com.roomx.shared.dto.booking.response.ServiceRequestResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface ServiceRequestAppMapper {
    ServiceRequest toDomain(ServiceBookingRequest request);

    @Mapping(target = "serviceCode", source = "service.serviceCode")
    @Mapping(target = "unitPrice", source = "service.price.unitPrice")
    @Mapping(target = "id", source = "service.id")
    @Mapping(target = "name", source = "service.name")
    @Mapping(target = "imageUrls", source = "service.imageUrls")
    ServiceRequestResponse toResponse(ServiceRequest domain);

}
