package com.roomx.infrastructure.persistence.service;


import com.roomx.domain.model.entity.ServicePriceHistory;
import com.roomx.infrastructure.persistence.dto.ServiceFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ServicePriceHistoryEntityService {
    Page<ServicePriceHistory> searchFilterService(ServiceFilter filter, Pageable pageable);
}
