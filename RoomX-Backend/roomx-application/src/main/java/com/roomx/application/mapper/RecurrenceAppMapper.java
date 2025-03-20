package com.roomx.application.mapper;

import com.roomx.shared.dto.booking.request.RecurrenceCreateRequest;
import com.roomx.shared.dto.booking.response.RecurrenceResponse;
import com.roomx.domain.model.entity.Recurrence;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface RecurrenceAppMapper {
    Recurrence toDomain(RecurrenceCreateRequest request);

    RecurrenceResponse toResponse(Recurrence domain);
}
