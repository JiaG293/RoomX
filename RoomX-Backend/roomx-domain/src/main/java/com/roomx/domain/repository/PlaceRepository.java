package com.roomx.domain.repository;

import com.roomx.domain.model.aggrerate.Place;


import java.util.Optional;

public interface PlaceRepository {
    Optional<Place> findById(String id);
    Optional<Place> findBySlug(String slug);
    Optional<Place> findByName(String name);
    Place save(Place place);
    boolean checkPlaceExistsBySlug(String slug);

}
