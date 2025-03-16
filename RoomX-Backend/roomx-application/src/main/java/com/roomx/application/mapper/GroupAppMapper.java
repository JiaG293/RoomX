package com.roomx.application.mapper;

import com.roomx.application.dto.user.request.GroupCreateRequest;
import com.roomx.application.dto.user.response.GroupResponse;
import com.roomx.domain.model.aggrerate.Group;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface GroupAppMapper {

    Group toDomain(GroupCreateRequest request);

    GroupResponse toResponse(Group domain);
}
