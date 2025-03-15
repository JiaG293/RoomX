package com.roomx.infrastructure.multitenancy.persistence.service.impl;

import com.roomx.domain.model.aggrerate.Branch;
import com.roomx.domain.model.aggrerate.Place;
import com.roomx.infrastructure.multitenancy.persistence.dto.PlaceFilter;
import com.roomx.infrastructure.multitenancy.persistence.mapper.PlaceEntityMapper;
import com.roomx.infrastructure.multitenancy.persistence.model.base.GenericSpecification;
import com.roomx.infrastructure.multitenancy.persistence.model.base.SearchCriteria;
import com.roomx.infrastructure.multitenancy.persistence.model.entity.PlaceEntity;
import com.roomx.infrastructure.multitenancy.persistence.model.entity.ServiceEntity;
import com.roomx.infrastructure.multitenancy.persistence.repository.jpa.JpaPlaceEntityRepository;
import com.roomx.infrastructure.multitenancy.persistence.service.PlaceEntityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.AbstractMap;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlaceEntityServiceImpl implements PlaceEntityService {
    private final JpaPlaceEntityRepository jpaPlaceEntityRepository;
    private final PlaceEntityMapper placeEntityMapper;

    @Override
    public Page<Place> filterPagePlaces(PlaceFilter filter, Pageable pageable, boolean typeCompare) {
        // use filter search like, AND
        String method = "%";
        boolean condition = typeCompare; // TRUE = OR || FALSE = AND
        List<SearchCriteria> filters = Stream.of(
                        new AbstractMap.SimpleEntry<>("branch.branchCode", filter.getBranchCode()),
                        new AbstractMap.SimpleEntry<>("id", filter.getId()),
                        new AbstractMap.SimpleEntry<>("slug", filter.getSlug()),
                        new AbstractMap.SimpleEntry<>("floor", filter.getFloor()),
                        new AbstractMap.SimpleEntry<>("building", filter.getBuilding()),
                        new AbstractMap.SimpleEntry<>("name", filter.getName())
                ).filter(entry -> entry.getValue() != null && !entry.getValue().toString().isBlank())
                .map(entry -> new SearchCriteria(entry.getKey(), entry.getValue(), method, condition))
                .toList();

        log.info("Searching data: {}", filters);

        Specification<PlaceEntity> spec = new GenericSpecification<>(filters);

        var placeEntityPage = jpaPlaceEntityRepository.findAll(spec, pageable);


        return placeEntityPage.map(placeEntityMapper::toDomain);
    }
}
