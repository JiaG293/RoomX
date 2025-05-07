package com.roomx.application.mapper;

import com.roomx.infrastructure.persistence.model.projection.RoomProjection;
import com.roomx.shared.dto.resource.request.RoomCreateRequest;
import com.roomx.shared.dto.resource.response.RoomDetailResponse;
import com.roomx.shared.dto.resource.response.RoomFilterResponse;
import com.roomx.shared.dto.resource.response.RoomResponse;
import com.roomx.domain.model.aggrerate.Room;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {
                PlaceAppMapper.class,
                RoomClassAppMapper.class,
                RoomClassPriceHistoryAppMapper.class,
                EquipmentAppMapper.class,
                ServiceAppMapper.class
        }
)
public interface RoomAppMapper {

    Room toDomain(RoomCreateRequest request);

//    @Mapping(target = "roomName", expression = "java(domain.getRoomName())")
//    @Mapping(target = "totalPrice", expression = "java(domain.getTotalPrice())")
    @Mapping(target = "roomClassId", source = "roomClass.id")
    @Mapping(target = "placeId", source = "place.id")
    RoomResponse toResponse(Room domain);

    RoomDetailResponse toResponseDetail(Room domain);


    @Mapping(target = "buildingPlaceId", source = "buildingPlaceId")
    RoomFilterResponse toResponseFilter(RoomProjection roomProjection);
}
