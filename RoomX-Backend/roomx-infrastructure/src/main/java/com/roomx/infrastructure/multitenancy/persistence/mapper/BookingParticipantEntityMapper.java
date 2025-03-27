package com.roomx.infrastructure.multitenancy.persistence.mapper;

import com.roomx.domain.model.entity.BookingParticipant;
import com.roomx.infrastructure.multitenancy.persistence.model.entity.BookingParticipantEntity;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {
                UserEntityMapper.class,
                BookingEntityMapper.class,
                BookingParticipantEntityIdMapper.class
        }
)
public interface BookingParticipantEntityMapper {
    BookingParticipant toDomain(BookingParticipantEntity entity);

    BookingParticipantEntity toEntity(BookingParticipant domain);
}
