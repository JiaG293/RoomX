package com.roomx.infrastructure.multitenancy.persistence.service;

import com.roomx.domain.model.aggrerate.Branch;
import com.roomx.infrastructure.multitenancy.persistence.dto.PlaceFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;



public interface PlaceEntityService {
    public Page<Branch> filterPagePlaces(PlaceFilter filter, Pageable pageable, boolean typeCompare);
}
