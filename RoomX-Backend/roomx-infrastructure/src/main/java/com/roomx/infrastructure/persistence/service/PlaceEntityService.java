package com.roomx.infrastructure.persistence.service;

import com.roomx.domain.model.aggrerate.Branch;
import com.roomx.domain.model.aggrerate.Place;
import com.roomx.infrastructure.persistence.dto.PlaceFilter;
import com.roomx.shared.dto.resource.request.PlaceCreateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;



public interface PlaceEntityService {
    Page<Place> filterSearchPagePlaces(PlaceFilter filter, Pageable pageable);
    Place createPlaceFloor(Branch branchDomain, PlaceCreateRequest request);
    Place createPlaceBuilding(Branch branchDomain, PlaceCreateRequest request);
    Place createPlaceBranch(Branch branchDomain, PlaceCreateRequest request);
}
