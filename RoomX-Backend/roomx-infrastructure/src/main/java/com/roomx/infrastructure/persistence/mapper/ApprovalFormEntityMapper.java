package com.roomx.infrastructure.persistence.mapper;

import com.roomx.domain.model.aggrerate.ApprovalForm;
import com.roomx.infrastructure.persistence.model.entity.ApprovalFormEntity;
import com.roomx.shared.dto.booking.response.BookingRequestResponse;
import com.roomx.shared.dto.booking.response.BookingResponse;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {
            BookingRequestEntityMapper.class
        }
)
public interface ApprovalFormEntityMapper {

    @Mapping(target = "bookingRequest", source = "bookingRequest")
    ApprovalForm toDomain(ApprovalFormEntity entity);


    @Mapping(target = "bookingRequest", ignore = true)
    ApprovalForm toDomainMin(ApprovalFormEntity entity);

    ApprovalFormEntity toEntity(ApprovalForm domain);

    @AfterMapping
    default void updateApprovalStatus(@MappingTarget ApprovalForm target, ApprovalFormEntity entity) {
        if (target.getBookingRequest() != null) {
            target.getBookingRequest().setApprovalStatus(entity.getStatus());
        }
    }


}
