package com.roomx.infrastructure.multitenancy.persistence.mapper;

import com.roomx.domain.model.aggrerate.ApprovalForm;
import com.roomx.infrastructure.multitenancy.persistence.model.entity.ApprovalFormEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface ApprovalFormEntityMapper {
    ApprovalForm toDomain(ApprovalFormEntity entity);
    ApprovalFormEntity toEntity(ApprovalForm domain);
}
