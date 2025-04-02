package com.roomx.infrastructure.persistence.mapper;

import com.roomx.domain.model.aggrerate.Service;
import com.roomx.domain.model.entity.ServicePriceHistory;
import com.roomx.infrastructure.persistence.model.entity.ServiceEntity;
import com.roomx.infrastructure.persistence.model.entity.ServicePriceHistoryEntity;
import org.mapstruct.*;

import java.util.Comparator;
import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {
                ServicePriceHistoryEntityMapper.class
        }
)
public interface ServiceEntityMapper {

    @Mapping(target = "id", source = "id")
    Service toDomain(ServiceEntity entity);

    @Named("toDomainSpec")
    @Mapping(target = "price", expression = "java(getLatestActivePrice(entity.getPriceHistories()))")
    Service toDomainSpec(ServiceEntity entity);

    @Mapping(target = "id", source = "id")
    ServiceEntity toEntity(Service domain);


    default ServicePriceHistory getLatestActivePrice(List<ServicePriceHistoryEntity> priceHistories) {
        return priceHistories.stream()
                .filter(servicePrice -> servicePrice.getValidEnd() == null)
                .max(Comparator.comparing(ServicePriceHistoryEntity::getValidFrom))
                .map(servicePrice -> ServicePriceHistory.builder()
                        .id(servicePrice.getId())
                        .unitPrice(servicePrice.getUnitPrice())
                        .validEnd(servicePrice.getValidEnd())
                        .validFrom(servicePrice.getValidFrom())
                        .build())
                .orElse(null);
    }
}
