package com.roomx.application.mapper;

import com.roomx.domain.model.aggrerate.ApprovalForm;
import com.roomx.domain.model.aggrerate.Equipment;
import com.roomx.shared.dto.booking.request.BookingRequestCreateRequest;
import com.roomx.shared.dto.booking.request.BookingRequestUserCreateRequest;
import com.roomx.shared.dto.booking.request.CheckingBookingRequest;
import com.roomx.shared.dto.booking.response.BookingRequestResponse;
import com.roomx.domain.model.aggrerate.BookingRequest;
import com.roomx.shared.dto.booking.response.BookingUserRelatedResponse;
import org.mapstruct.*;

import java.time.Instant;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {
                EquipmentRequestAppMapper.class,
                ServiceRequestAppMapper.class,
                DateRequestExceptionAppMapper.class
        }
)
public interface BookingRequestAppMapper {

    @Mapping(target = "services", ignore = true)
    @Mapping(target = "equipments", ignore = true)
    BookingRequest toDomainUser(BookingRequestUserCreateRequest request);

    BookingRequest toDomain(BookingRequestCreateRequest request);

    BookingRequestResponse toResponse(BookingRequest domain);

    @Mapping(target = "createdAt", expression = "java(approvalForm.getCreatedAt())")
    @Mapping(target = "updatedAt", expression = "java(approvalForm.getUpdatedAt())")
    BookingRequestResponse toResponseFromApprovalForm(BookingRequest domain, @Context ApprovalForm approvalForm);

    @AfterMapping
    default void setUpdatedAt(@MappingTarget BookingRequest domain) {
        domain.setUpdatedAt(Instant.now());
    }

    @Mapping(target = "createdAt", expression = "java(approvalForm.getCreatedAt())")
    @Mapping(target = "updatedAt", expression = "java(approvalForm.getUpdatedAt())")
    BookingUserRelatedResponse toResponseFromApprovalFormForUser(BookingRequest bookingRequest, @Context ApprovalForm approvalForm);

    BookingRequest toDomainChecking(CheckingBookingRequest request);
}
