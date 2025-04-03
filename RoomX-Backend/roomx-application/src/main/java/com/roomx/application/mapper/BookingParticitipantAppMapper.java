package com.roomx.application.mapper;


import com.roomx.domain.model.aggrerate.User;
import com.roomx.domain.model.entity.BookingParticipant;
import com.roomx.shared.dto.booking.response.BookingParticipantResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {
                UserAppMapper.class
        }
)
public interface BookingParticitipantAppMapper {


    @Mapping(target = "participantId", source = "user.id")
    @Mapping(target = "userCode", source = "user.userCode")
    @Mapping(target = "email", source = "user.email")
    BookingParticipantResponse toResponse(BookingParticipant domain);
}
