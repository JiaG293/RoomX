package com.roomx.application.mapper;

import com.roomx.shared.dto.resource.request.PlaceCreateRequest;
import com.roomx.shared.dto.resource.request.PlaceUpdateRequest;
import com.roomx.shared.dto.resource.response.PlaceBranchResponse;
import com.roomx.shared.dto.resource.response.PlaceHierarchyResponse;
import com.roomx.shared.dto.resource.response.PlaceResponse;
import com.roomx.domain.model.aggrerate.Place;
import org.mapstruct.*;

import java.util.*;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {
                BranchAppMapper.class
        }
)
public interface PlaceAppMapper {

    @Mapping(target = "branch", ignore = true)
    Place toDomain(PlaceCreateRequest request);

    PlaceResponse toResponse(Place domain);

    @Mapping(target = "children", expression = "java(buildChildren(place, places))")
    @Mapping(target = "branch", expression = "java(place.getParentId() == null ? branchAppMapper.toResponse(place.getBranch()) : null)")
    PlaceHierarchyResponse toResponseHierarchy(Place place, List<Place> places);

    @Mapping(target = "branchId", source = "branch.id")
    PlaceBranchResponse toResponseBranch(Place domain);

    @Mapping(target = "id", ignore = true)
    void updateDomainFromDto(PlaceUpdateRequest request, @MappingTarget Place domain);


    default List<PlaceHierarchyResponse> buildChildren(Place place, List<Place> places) {
        List<PlaceHierarchyResponse> children = new ArrayList<>();
        for (Place p : places) {
            if (p.getParentId() != null && p.getParentId().equals(place.getId())) {
                children.add(toResponseHierarchy(p, places));
            }
        }
        return children;
    }

    default List<PlaceHierarchyResponse> buildHierarchy(List<Place> places) {
        List<PlaceHierarchyResponse> rootPlaces = new ArrayList<>();
        for (Place place : places) {
            if (place.getParentId() == null) {
                rootPlaces.add(toResponseHierarchy(place, places));
            }
        }
        return rootPlaces;
    }
}
