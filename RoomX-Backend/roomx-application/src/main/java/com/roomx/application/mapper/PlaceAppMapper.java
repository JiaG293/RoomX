package com.roomx.application.mapper;

import com.roomx.application.dto.resource.request.PlaceCreateRequest;
import com.roomx.application.dto.resource.request.PlaceUpdateRequest;
import com.roomx.application.dto.resource.response.PlaceResponse;
import com.roomx.domain.model.aggrerate.Branch;
import com.roomx.domain.model.aggrerate.Place;
import org.mapstruct.*;

import java.time.Instant;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {
                BranchAppMapper.class
        }
)
public interface PlaceAppMapper {

    @Mapping(target = "branch", ignore = true)
    Place toDomain(PlaceCreateRequest request);

    PlaceResponse toResponse(Place domain);

    @Mapping(target = "id", ignore = true)
    void updateDomainFromDto(PlaceUpdateRequest request, @MappingTarget Place domain);
    

}
