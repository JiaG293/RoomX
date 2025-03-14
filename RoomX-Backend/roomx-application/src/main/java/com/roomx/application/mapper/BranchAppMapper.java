package com.roomx.application.mapper;

import com.roomx.application.dto.resource.request.BranchCreateRequest;
import com.roomx.application.dto.resource.request.BranchUpdateRequest;
import com.roomx.application.dto.resource.response.BranchResponse;
import com.roomx.domain.model.aggrerate.Branch;
import com.roomx.domain.model.aggrerate.Equipment;
import com.roomx.infrastructure.multitenancy.persistence.model.entity.BranchEntity;
import org.mapstruct.*;

import java.time.Instant;

@Mapper(componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface BranchAppMapper {

    Branch toDomain(BranchCreateRequest request);


    BranchEntity toEntity(Branch domain);


    @Mapping(target = "branchId", source = "id")
    BranchResponse toResponse(Branch domain);

    @Mapping(target = "id", ignore = true)
    void updateDomainFromDto(BranchUpdateRequest request, @MappingTarget Branch domain);

    @AfterMapping
    default void setUpdatedAt(@MappingTarget Branch domain) {
        domain.setUpdatedAt(Instant.now());
    }
}
