package com.roomx.application.mapper;

import com.roomx.application.dto.resource.request.EquipmentCreateRequest;
import com.roomx.application.dto.resource.request.EquipmentUpdateRequest;
import com.roomx.application.dto.resource.response.EquipmentResponse;
import com.roomx.domain.model.aggrerate.Equipment;
import com.roomx.domain.model.aggrerate.Service;
import org.mapstruct.*;

import java.time.Instant;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface EquipmentAppMapper {

    Equipment toDomain(EquipmentCreateRequest request);


    EquipmentResponse toResponse(Equipment domain);

    @Mapping(target = "id", ignore = true)
    void updateDomainFromDto(EquipmentUpdateRequest request, @MappingTarget Equipment domain);

    @AfterMapping
    default void setUpdatedAt(@MappingTarget Equipment domain) {
        domain.setUpdatedAt(Instant.now());
    }

}
