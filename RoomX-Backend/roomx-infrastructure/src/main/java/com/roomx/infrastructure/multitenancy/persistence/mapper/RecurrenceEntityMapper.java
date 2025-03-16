package com.roomx.infrastructure.multitenancy.persistence.mapper;

import com.roomx.domain.model.entity.Recurrence;
import com.roomx.infrastructure.multitenancy.persistence.model.entity.RecurrenceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface RecurrenceEntityMapper {
    Recurrence toDomain(RecurrenceEntity entity);

    RecurrenceEntity toEntity(Recurrence domain);
}
