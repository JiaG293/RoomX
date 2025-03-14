package com.roomx.application.mapper;

import com.roomx.application.dto.resource.request.ServiceCreateRequest;
import com.roomx.application.dto.resource.request.ServiceUpdateRequest;
import com.roomx.application.dto.resource.response.ServiceResponse;
import com.roomx.domain.model.aggrerate.Service;
import org.mapstruct.*;

import java.time.Instant;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface ServiceAppMapper {

    @Mapping(target = "id", source = "id")
    ServiceResponse toResponse(Service domain);

    Service toDomain(ServiceCreateRequest request);

    @Mapping(target = "id", ignore = true)
    void updateDomainFromDto(ServiceUpdateRequest request, @MappingTarget Service domain);

    @AfterMapping
    default void setUpdatedAt(@MappingTarget Service domain) {
        domain.setUpdatedAt(Instant.now());
    }

}
