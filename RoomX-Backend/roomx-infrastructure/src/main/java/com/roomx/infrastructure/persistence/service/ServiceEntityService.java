package com.roomx.infrastructure.persistence.service;



import com.roomx.domain.model.aggrerate.Service;
import com.roomx.infrastructure.persistence.dto.ServiceFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ServiceEntityService {
    Page<Service> searchFilterService(ServiceFilter filter, Pageable pageable);
}
