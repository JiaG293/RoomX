package com.roomx.infrastructure.multitenancy.persistence.service;

import com.roomx.domain.model.aggrerate.Branch;
import com.roomx.domain.model.aggrerate.Place;
import com.roomx.infrastructure.multitenancy.persistence.dto.PlaceFilter;
import com.roomx.shared.dto.resource.request.PlaceCreateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;



public interface PlaceEntityService {
    Page<Place> filterPagePlaces(PlaceFilter filter, Pageable pageable, boolean typeCompare);
    Place createPlaceFloor(Branch branchDomain, PlaceCreateRequest request);
    Place createPlaceBuilding(Branch branchDomain, PlaceCreateRequest request);
    Place createPlaceBranch(Branch branchDomain, PlaceCreateRequest request);
}
