package com.roomx.application.mapper;

import com.roomx.shared.dto.resource.request.BranchCreateRequest;
import com.roomx.shared.dto.resource.request.BranchUpdateRequest;
import com.roomx.shared.dto.resource.response.BranchDetailResponse;
import com.roomx.shared.dto.resource.response.BranchResponse;
import com.roomx.domain.model.aggrerate.Branch;
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


    @Mapping(target = "places", ignore = true)
    BranchDetailResponse toDetailResponse(Branch branchDomain);

    BranchResponse toResponse(Branch domain);

    @Mapping(target = "id", ignore = true)
    void updateDomainFromDto(BranchUpdateRequest request, @MappingTarget Branch domain);

    @AfterMapping
    default void setUpdatedAt(@MappingTarget Branch domain) {
        domain.setUpdatedAt(Instant.now());
    }

}
