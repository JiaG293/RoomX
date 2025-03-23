package com.roomx.application.mapper;

import com.roomx.shared.dto.resource.request.RoomClassCreateRequest;
import com.roomx.shared.dto.resource.request.RoomClassUpdateRequest;
import com.roomx.shared.dto.resource.response.RoomClassResponse;
import com.roomx.domain.model.aggrerate.RoomClass;
import org.mapstruct.*;

import java.time.Instant;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {
                EquipmentRoomClassAppMapper.class,
                RoomClassPriceHistoryAppMapper.class
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
