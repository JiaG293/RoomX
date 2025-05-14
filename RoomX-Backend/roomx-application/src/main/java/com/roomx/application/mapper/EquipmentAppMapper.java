package com.roomx.application.mapper;

import com.roomx.shared.dto.resource.request.EquipmentCreateRequest;
import com.roomx.shared.dto.resource.request.EquipmentUpdateRequest;
import com.roomx.shared.dto.resource.response.EquipmentDetailResponse;
import com.roomx.shared.dto.resource.response.EquipmentResponse;
import com.roomx.domain.model.aggrerate.Equipment;
import org.mapstruct.*;

import java.time.Instant;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {
                EquipmentPriceHistoryAppMapper.class
        }
)
public interface EquipmentAppMapper {

    Equipment toDomain(EquipmentCreateRequest request);

    EquipmentResponse toResponse(Equipment domain);

    @Mapping(target = "unitPrice", source = "price.unitPrice")
    EquipmentDetailResponse toResponseDetail(Equipment domain);

    @Mapping(target = "id", ignore = true)
    void updateDomainFromDto(EquipmentUpdateRequest request, @MappingTarget Equipment domain);

    @AfterMapping
    default void setUpdatedAt(@MappingTarget Equipment domain) {
        domain.setUpdatedAt(Instant.now());
    }

}
