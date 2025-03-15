package com.roomx.application.mapper;

import com.roomx.application.dto.resource.request.RoomClassCreateRequest;
import com.roomx.application.dto.resource.request.RoomClassUpdateRequest;
import com.roomx.application.dto.resource.response.EquipmentRoomClassResponse;
import com.roomx.application.dto.resource.response.RoomClassDetailResponse;
import com.roomx.application.dto.resource.response.RoomClassResponse;
import com.roomx.domain.model.aggrerate.RoomClass;
import com.roomx.domain.model.entity.EquipmentRoomClass;
import org.mapstruct.*;

import java.time.Instant;
import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {
                EquipmentRoomClassAppMapper.class
        }
)
public interface RoomClassAppMapper {

    RoomClass toDomain(RoomClassCreateRequest request);

    RoomClassResponse toResponse(RoomClass domain);

    /*@Mapping(target = "roomClass", source = "domain")
    @Mapping(target = "equipmentRoomClasses", source = "listEquipmentRoomClass")*/
//    void toResponseDetail(RoomClass domain, List<EquipmentRoomClass> listEquipmentRoomClass, @MappingTarget RoomClassDetailResponse response);

    @Mapping(target = "id", ignore = true)
    void updateDomainFromDto(RoomClassUpdateRequest request, @MappingTarget RoomClass domain);

    @AfterMapping
    default void setUpdatedAt(@MappingTarget RoomClass domain) {
        domain.setUpdatedAt(Instant.now());
    }
}
