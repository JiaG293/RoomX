package com.roomx.application.mapper;

import com.roomx.shared.dto.resource.request.ServiceCreateRequest;
import com.roomx.shared.dto.resource.request.ServiceUpdateRequest;
import com.roomx.shared.dto.resource.response.ServiceDetailResponse;
import com.roomx.shared.dto.resource.response.ServiceResponse;
import com.roomx.domain.model.aggrerate.Service;
import org.mapstruct.*;

import java.time.Instant;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {
                ServicePriceHistoryAppMapper.class
        }
)
public interface ServiceAppMapper {

    @Mapping(target = "id", source = "id")
    ServiceResponse toResponse(Service domain);

    ServiceDetailResponse toResponseDetail(Service domain);

    Service toDomain(ServiceCreateRequest request);

    @Mapping(target = "id", ignore = true)
    void updateDomainFromDto(ServiceUpdateRequest request, @MappingTarget Service domain);

    @AfterMapping
    default void setUpdatedAt(@MappingTarget Service domain) {
        domain.setUpdatedAt(Instant.now());
    }

}
