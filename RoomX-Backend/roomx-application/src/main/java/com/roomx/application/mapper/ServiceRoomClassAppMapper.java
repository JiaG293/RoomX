package com.roomx.application.mapper;

import com.roomx.application.dto.resource.request.ServiceRoomClassCreateRequest;
import com.roomx.application.dto.resource.response.ServiceRoomClassDetailResponse;
import com.roomx.application.dto.resource.response.ServiceRoomClassResponse;
import com.roomx.domain.model.entity.ServiceRoomClass;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {
                ServiceAppMapper.class,
                RoomClassAppMapper.class
        }
)
public interface ServiceRoomClassAppMapper {

    @Mapping(target = "totalPrice", expression = "java(domain.getTotalPrice())")
    ServiceRoomClassDetailResponse toResponseDetail(ServiceRoomClass domain);

    @Mapping(target = "totalPrice", expression = "java(domain.getTotalPrice())")
    @Mapping(target = "roomClass", ignore = true)
    ServiceRoomClassDetailResponse toResponseDetailWithoutRoomClass(ServiceRoomClass domain);

    @Mappings({
            @Mapping(target = "totalPrice", expression = "java(domain.getTotalPrice())"),
            @Mapping(target = "serviceId", source = "service.id"),
            @Mapping(target = "roomClassId", source = "roomClass.id")
    })
    ServiceRoomClassResponse toResponse(ServiceRoomClass domain);

    ServiceRoomClass toDomain(ServiceRoomClassCreateRequest request);
}
