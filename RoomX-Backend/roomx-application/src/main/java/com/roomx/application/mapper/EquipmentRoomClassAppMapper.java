package com.roomx.application.mapper;

import com.roomx.shared.dto.resource.request.EquipmentRoomClassCreateRequest;
import com.roomx.shared.dto.resource.response.EquipmentRoomClassDetailResponse;
import com.roomx.shared.dto.resource.response.EquipmentRoomClassResponse;
import com.roomx.domain.model.entity.EquipmentRoomClass;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {
                EquipmentAppMapper.class,
                RoomClassAppMapper.class,
                RoomClassPriceHistoryAppMapper.class
        }
)
public interface EquipmentRoomClassAppMapper {

//    @Mapping(target = "totalPrice", expression = "java(domain.getTotalPrice())")
    EquipmentRoomClassDetailResponse toResponseDetail(EquipmentRoomClass domain);

//    @Mapping(target = "totalPrice", expression = "java(domain.getTotalPrice())")
    @Mapping(target = "roomClass", ignore = true)
    EquipmentRoomClassDetailResponse toResponseDetailWithoutRoomClass(EquipmentRoomClass domain);

    @Mappings({
            @Mapping(target = "unitPrice", source = "price.unitPrice"),
            @Mapping(target = "equipmentId", source = "equipment.id")
    })
    EquipmentRoomClassResponse toResponse(EquipmentRoomClass domain);


    EquipmentRoomClass toDomain(EquipmentRoomClassCreateRequest request);

}
