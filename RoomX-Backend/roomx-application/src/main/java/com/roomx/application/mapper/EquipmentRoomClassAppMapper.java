package com.roomx.application.mapper;

import com.roomx.shared.dto.resource.request.EquipmentRoomClassCreateRequest;
import com.roomx.shared.dto.resource.response.EquipmentDetailResponse;
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
    @Mapping(target = "id", source = "equipment.id")
    @Mapping(target = "equipmentCode", source = "equipment.equipmentCode")
    @Mapping(target = "name", source = "equipment.name")
    @Mapping(target = "brand", source = "equipment.brand")
    @Mapping(target = "description", source = "equipment.description")
    @Mapping(target = "imageUrls", source = "equipment.imageUrls")
    @Mapping(target = "unitPrice", source = "equipment.price.unitPrice")
    EquipmentRoomClassDetailResponse toResponseDetail(EquipmentRoomClass domain);


    @Mappings({
            @Mapping(target = "unitPrice", source = "price.unitPrice"),
            @Mapping(target = "equipmentId", source = "equipment.id"),
    })
    EquipmentRoomClassResponse toResponse(EquipmentRoomClass domain);


    EquipmentRoomClass toDomain(EquipmentRoomClassCreateRequest request);

}
