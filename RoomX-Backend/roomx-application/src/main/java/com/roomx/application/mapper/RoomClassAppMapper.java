package com.roomx.application.mapper;

import com.roomx.application.dto.resource.request.RoomClassCreateRequest;
import com.roomx.application.dto.resource.request.RoomClassUpdateRequest;
import com.roomx.application.dto.resource.request.ServiceUpdateRequest;
import com.roomx.application.dto.resource.response.RoomClassResponse;
import com.roomx.domain.model.aggrerate.RoomClass;
import org.mapstruct.*;

import java.time.Instant;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface RoomClassAppMapper {

    RoomClass toDomain(RoomClassCreateRequest request);

    RoomClassResponse toResponse(RoomClass domain);

    @Mapping(target = "id", ignore = true)
    void updateDomainFromDto(RoomClassUpdateRequest request, @MappingTarget RoomClass domain);

    @AfterMapping
    default void setUpdatedAt(@MappingTarget RoomClass domain) {
        domain.setUpdatedAt(Instant.now());
    }
}
