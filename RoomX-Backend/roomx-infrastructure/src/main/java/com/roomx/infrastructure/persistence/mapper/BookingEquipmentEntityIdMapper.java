package com.roomx.infrastructure.persistence.mapper;

import com.roomx.domain.model.vo.BookingEquipmentId;
import com.roomx.infrastructure.persistence.model.ids.BookingEquipmentEntityId;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface BookingEquipmentEntityIdMapper {
    BookingEquipmentId toDomain(BookingEquipmentEntityId entity);
    BookingEquipmentEntityId toEntity(BookingEquipmentId domain);
}
