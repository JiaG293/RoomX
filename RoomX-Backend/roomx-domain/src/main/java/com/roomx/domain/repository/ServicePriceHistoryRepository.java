package com.roomx.domain.repository;


import com.roomx.domain.model.entity.ServicePriceHistory;


import java.util.Optional;

public interface ServicePriceHistoryRepository {
    Optional<ServicePriceHistory> findById(String id);

    ServicePriceHistory save(ServicePriceHistory servicePriceHistory);

    Optional<ServicePriceHistory> findLatestValidFrom(String serviceId);
}
