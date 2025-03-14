package com.roomx.infrastructure.multitenancy.persistence.service;



import com.roomx.domain.model.aggrerate.Service;
import com.roomx.infrastructure.multitenancy.persistence.dto.ServiceFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ServiceEntityService {
    public Page<Service> filterPageServices(ServiceFilter filter, Pageable pageable, boolean typeCompare);
}
