package com.roomx.application.mapper;

import com.roomx.domain.model.entity.RoomClassPriceHistory;
import com.roomx.shared.dto.resource.request.RoomClassPriceHistoryCreateRequest;
import com.roomx.shared.dto.resource.response.RoomClassPriceHistoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface RoomClassPriceHistoryAppMapper {
    RoomClassPriceHistory toDomain(RoomClassPriceHistoryCreateRequest request);
    RoomClassPriceHistoryResponse toRequest(RoomClassPriceHistory domain);
}
