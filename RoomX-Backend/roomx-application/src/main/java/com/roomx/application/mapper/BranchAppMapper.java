package com.roomx.application.mapper;

import com.roomx.application.dto.resource.response.BranchReponse;
import com.roomx.domain.model.aggrerate.Branch;
import com.roomx.infrastructure.multitenancy.persistence.model.entity.BranchEntity;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface BranchAppMapper {

    Branch toDomain(BranchEntity entity);


    BranchEntity toEntity(Branch domain);


    BranchReponse toReponse(Branch domain);
}
