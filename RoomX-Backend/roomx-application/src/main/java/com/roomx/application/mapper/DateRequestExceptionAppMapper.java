package com.roomx.application.mapper;

import com.roomx.domain.model.entity.DateRequestException;
import com.roomx.shared.dto.booking.request.DateRequestExceptionRequest;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface DateRequestExceptionAppMapper {


    DateRequestException toDomain(DateRequestExceptionRequest request);
}
