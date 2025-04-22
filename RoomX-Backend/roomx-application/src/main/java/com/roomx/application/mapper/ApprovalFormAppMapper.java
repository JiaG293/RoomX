package com.roomx.application.mapper;

import com.roomx.shared.dto.booking.request.ApprovalFormCreateRequest;
import com.roomx.shared.dto.booking.response.ApprovalFormMinResponse;
import com.roomx.shared.dto.booking.response.ApprovalFormResponse;
import com.roomx.domain.model.aggrerate.ApprovalForm;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface ApprovalFormAppMapper {
    ApprovalForm toDomain(ApprovalFormCreateRequest request);

    @Mapping(target = "bookingRequestId", source = "bookingRequest.id")
    ApprovalFormResponse toResponse(ApprovalForm domain);


}
