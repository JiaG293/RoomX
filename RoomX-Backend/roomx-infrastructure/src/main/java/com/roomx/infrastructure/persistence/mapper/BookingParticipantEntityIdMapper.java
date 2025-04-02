package com.roomx.infrastructure.persistence.mapper;

import com.roomx.domain.model.vo.BookingParticipantId;
import com.roomx.infrastructure.persistence.model.ids.BookingParticipantEntityId;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface BookingParticipantEntityIdMapper {
    BookingParticipantId toDomain(BookingParticipantEntityId entity);

    BookingParticipantEntityId toEntity(BookingParticipantId domain);

}
