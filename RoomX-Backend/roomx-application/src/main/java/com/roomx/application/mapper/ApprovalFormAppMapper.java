package com.roomx.application.mapper;

import com.roomx.application.dto.booking.request.ApprovalFormCreateRequest;
import com.roomx.application.dto.booking.response.ApprovalFormResponse;
import com.roomx.domain.model.entity.ApprovalForm;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface ApprovalFormAppMapper {
    ApprovalForm toDomain(ApprovalFormCreateRequest request);

    ApprovalFormResponse toResponse(ApprovalForm domain);


}
