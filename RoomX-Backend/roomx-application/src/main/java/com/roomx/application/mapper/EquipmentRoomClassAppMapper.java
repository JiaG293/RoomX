package com.roomx.application.mapper;

import com.roomx.application.dto.resource.request.EquipmentRoomClassCreateRequest;
import com.roomx.application.dto.resource.response.EquipmentRoomClassDetailResponse;
import com.roomx.application.dto.resource.response.EquipmentRoomClassResponse;
import com.roomx.domain.model.entity.EquipmentRoomClass;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {
                EquipmentAppMapper.class,
                RoomClassAppMapper.class
        }
)
public interface EquipmentRoomClassAppMapper {

    @Mapping(target = "totalPrice", expression = "java(domain.getTotalPrice())")
    EquipmentRoomClassDetailResponse toResponseDetail(EquipmentRoomClass domain);

    @Mappings({
            @Mapping(target = "totalPrice", expression = "java(domain.getTotalPrice())"),
            @Mapping(target = "equipmentId", source = "equipment.id"),
            @Mapping(target = "roomClassId", source = "roomClass.id")
    })
    EquipmentRoomClassResponse toResponse(EquipmentRoomClass domain);


    EquipmentRoomClass toDomain(EquipmentRoomClassCreateRequest request);

}
