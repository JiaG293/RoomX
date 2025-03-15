package com.roomx.infrastructure.multitenancy.persistence.mapper;

import com.roomx.domain.model.aggrerate.Place;
import com.roomx.infrastructure.multitenancy.persistence.model.entity.PlaceEntity;
import org.mapstruct.Mapper;
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
}
