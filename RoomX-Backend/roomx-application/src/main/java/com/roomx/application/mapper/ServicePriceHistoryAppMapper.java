package com.roomx.application.mapper;

import com.roomx.domain.model.entity.ServicePriceHistory;
import com.roomx.shared.dto.resource.request.ServicePriceHistoryCreateRequest;
import com.roomx.shared.dto.resource.response.PriceHistoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface ServicePriceHistoryAppMapper {
    PriceHistoryResponse toResponse(ServicePriceHistory domain);

    ServicePriceHistory toDomain(ServicePriceHistoryCreateRequest request);
}
