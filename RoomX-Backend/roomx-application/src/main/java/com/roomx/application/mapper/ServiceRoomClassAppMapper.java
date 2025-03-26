package com.roomx.application.mapper;

import com.roomx.shared.dto.resource.request.ServiceRoomClassCreateRequest;
import com.roomx.shared.dto.resource.response.ServiceDetailResponse;
import com.roomx.shared.dto.resource.response.ServiceRoomClassDetailResponse;
import com.roomx.shared.dto.resource.response.ServiceRoomClassResponse;
import com.roomx.domain.model.entity.ServiceRoomClass;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {
                ServiceAppMapper.class,
                RoomClassAppMapper.class,
                RoomClassPriceHistoryAppMapper.class
        }
)
public interface ServiceRoomClassAppMapper {

//    @Mapping(target = "totalPrice", expression = "java(domain.getTotalPrice())")
@Mapping(target = "id", source = "service.id")
@Mapping(target = "serviceCode", source = "service.serviceCode")
@Mapping(target = "name", source = "service.name")
@Mapping(target = "note", source = "service.note")
@Mapping(target = "description", source = "service.description")
@Mapping(target = "imageUrls", source = "service.imageUrls")
@Mapping(target = "unitPrice", source = "service.price.unitPrice")
    ServiceRoomClassDetailResponse toResponseDetail(ServiceRoomClass domain);

//    @Mapping(target = "totalPrice", expression = "java(domain.getTotalPrice())")
//    ServiceRoomClassDetailResponse toResponseDetailWithoutRoomClass(ServiceRoomClass domain);

    @Mappings({
//            @Mapping(target = "totalPrice", expression = "java(domain.getTotalPrice())"),
            @Mapping(target = "unitPrice", source = "price.unitPrice"),
            @Mapping(target = "serviceId", source = "service.id")
    })
    ServiceRoomClassResponse toResponse(ServiceRoomClass domain);

    ServiceRoomClass toDomain(ServiceRoomClassCreateRequest request);
}
