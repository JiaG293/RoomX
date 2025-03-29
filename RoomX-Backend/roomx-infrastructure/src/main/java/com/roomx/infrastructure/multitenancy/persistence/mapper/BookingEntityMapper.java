package com.roomx.infrastructure.multitenancy.persistence.mapper;


import com.roomx.domain.model.aggrerate.Booking;

import com.roomx.infrastructure.multitenancy.persistence.model.entity.BookingEntity;
import org.mapstruct.Mapper;

import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE/*,
        uses = {
                UserEntityMapper.class,
                BookingParticipantEntityMapper.class,
                BookingParticipantEntityIdMapper.class,
                RoomEntityMapper.class,
                BookingRequestEntityMapper.class
        }*/
)
public interface BookingEntityMapper {


    Booking toDomain(BookingEntity entity);

    BookingEntity toEntity(Booking domain);
}
