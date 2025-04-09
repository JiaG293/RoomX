package com.roomx.domain.repository;

import com.roomx.domain.model.aggrerate.Place;
import com.roomx.shared.enums.DeleteStatusType;
import com.roomx.shared.enums.PlaceType;

import java.util.List;
import java.util.Optional;


public interface PlaceRepository {
    Optional<Place> findById(String id);
    Optional<Place> findByIdAndStatus(String id, String status);
    Optional<Place> findByName(String name);
    Place save(Place place);
    Optional<Place> findByNameAndPlaceTypeAndBranchId(String name, String placeType, String branchId);
    Optional<Place> findByPlaceTypeAndCode(String placeType, String code);
    Optional<Place> findByPlaceTypeAndCodeAndStatus(String placeType, String code, String status);
    List<Place> saveAll(List<Place> listPlace);

    Optional<Place> findByPlaceTypeAndParentId(String placeType, String parentId);

    List<Place> findAll();

    Optional<Place> findByPlaceTypeAndParentIdAndCode(String placeType, String parentId, String code);


    Optional<Place> findAllByPlaceTypeAndStatus(String placeType, String status);

    List<Place> findRootPlace();

    List<Place> findChildrenPlace(String notPlaceType);

    Optional<Place> findByBranchIdAndPlaceTypeAndStatus(String branchId, String placeType, String status);

    Optional<Place> findByCode(String code);

    Optional<Place> findByPlaceTypeAndCodeAndParentId(String type, String code, String parentId);
    Optional<Place> findByIdAndPlaceTypeAndCode(String id, String placeType, String code);
    Optional<Place>findByIdAndStatusAndPlaceType(String placeId, String status, String placeType);

    List<Place> findAllByStatus(String status);


//    boolean checkPlaceExistsBySlug(String slug);
//    boolean checkPlaceExistsBySlugBuildingFloorBranchId(String BranchId, String slug, String Building, String floor);
//    List<String> customFindPlaceSelectBox(String branchId, String building, String floor, String placeType);
}
