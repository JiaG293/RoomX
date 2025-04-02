package com.roomx.infrastructure.persistence.mapper;

import com.roomx.domain.model.aggrerate.Place;
import com.roomx.infrastructure.persistence.model.dto.PlaceDto;
import com.roomx.infrastructure.persistence.model.entity.PlaceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {
                BranchEntityMapper.class
        }
)
public interface PlaceEntityMapper {

    Place toDomain(PlaceEntity entity);

    PlaceEntity toEntity(Place domain);

    @Mapping(target = "branch.id", source = "branchId")
    Place toPlaceDto(PlaceDto placeDto);

    @Mapping(target = "branch", ignore = true) // Nếu branch là lazy, nó sẽ bị load
    Place toDomainLazy(PlaceEntity entity);
}
