package com.roomx.infrastructure.multitenancy.persistence.mapper;

import com.roomx.domain.dto.BookingDto;
import com.roomx.domain.model.aggrerate.Booking;
import com.roomx.domain.model.entity.BookingParticipant;
import com.roomx.infrastructure.multitenancy.persistence.model.entity.BookingEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface BookingEntityMapper {


    Booking toDomain(BookingEntity entity);

    BookingEntity toEntity(Booking domain);
}
