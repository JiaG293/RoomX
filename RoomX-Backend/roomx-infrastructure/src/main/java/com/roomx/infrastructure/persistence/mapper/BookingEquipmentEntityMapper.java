package com.roomx.infrastructure.persistence.mapper;

import com.roomx.domain.model.entity.BookingEquipment;
import com.roomx.infrastructure.persistence.model.entity.BookingEquipmentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {
                EquipmentEntityMapper.class,
                BookingEntityMapper.class,
                BookingEquipmentEntityIdMapper.class
        }
)
public interface BookingEquipmentEntityMapper {
    BookingEquipment toDomain(BookingEquipmentEntity entity);

    BookingEquipmentEntity toEntity(BookingEquipment domain);
}
