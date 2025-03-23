package com.roomx.application.mapper;

import com.roomx.domain.model.entity.EquipmentPriceHistory;
import com.roomx.shared.dto.resource.request.EquipmentPriceHistoryCreateRequest;
import com.roomx.shared.dto.resource.response.EquipmentPriceHistoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface EquipmentPriceHistoryAppMapper {


    EquipmentPriceHistory toDomain(EquipmentPriceHistoryCreateRequest request);

    EquipmentPriceHistoryResponse toResponse(EquipmentPriceHistory domain);
}
