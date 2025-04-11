package com.roomx.infrastructure.persistence.mapper;


import com.roomx.domain.model.aggrerate.Booking;

import com.roomx.infrastructure.persistence.model.dto.BookingDto;
import com.roomx.infrastructure.persistence.model.entity.BookingEntity;
import com.roomx.infrastructure.persistence.model.projection.BookingProjection;
import com.roomx.shared.base.BookingListDto;
import org.mapstruct.Mapper;

import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
        ,
        uses = {
//                UserEntityMapper.class,
//                BookingParticipantEntityMapper.class,
//                BookingParticipantEntityIdMapper.class,
                RoomEntityMapper.class,
//                BookingRequestEntityMapper.class
        }
)
public interface BookingEntityMapper {


    Booking toDomain(BookingEntity entity);

    BookingEntity toEntity(Booking domain);


    BookingDto toDto(BookingProjection projection);
}
