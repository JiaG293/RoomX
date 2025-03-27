package com.roomx.application.mapper;

import com.roomx.shared.dto.booking.request.EquipmentBookingRequest;
import com.roomx.domain.model.entity.EquipmentRequest;
import com.roomx.shared.dto.booking.response.EquipmentRequestResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {
                EquipmentAppMapper.class
        }
)
public interface EquipmentRequestAppMapper {
    EquipmentRequest toDomain(EquipmentBookingRequest request);

    @Mapping(target = "equipmentCode", source = "equipment.equipmentCode")
    @Mapping(target = "unitPrice", source = "equipment.price.unitPrice")
    @Mapping(target = "id", source = "equipment.id")
    @Mapping(target = "name", source = "equipment.name")
    @Mapping(target = "imageUrls", source = "equipment.imageUrls")
    EquipmentRequestResponse toResponse(EquipmentRequest domain);
}
