package com.roomx.infrastructure.persistence.service;

import com.roomx.domain.model.aggrerate.Place;
import com.roomx.infrastructure.persistence.dto.PlaceFilter;
import com.roomx.shared.dto.resource.request.PlaceCreateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;



public interface PlaceEntityService {
    Page<Place> filterSearchPagePlaces(PlaceFilter filter, Pageable pageable);
    Place createPlaceFloor(PlaceCreateRequest request);
    Place createPlaceBuilding(PlaceCreateRequest request);
    Place createPlaceBranch(PlaceCreateRequest request);
}
