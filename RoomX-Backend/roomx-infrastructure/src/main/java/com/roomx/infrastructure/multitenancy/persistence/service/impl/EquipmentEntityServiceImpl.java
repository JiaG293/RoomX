package com.roomx.infrastructure.multitenancy.persistence.service.impl;

import com.roomx.domain.model.aggrerate.Equipment;
import com.roomx.infrastructure.multitenancy.persistence.dto.EquipmentFilter;
import com.roomx.infrastructure.multitenancy.persistence.mapper.EquipmentEntityMapper;
import com.roomx.infrastructure.multitenancy.persistence.model.base.GenericSpecification;
import com.roomx.infrastructure.multitenancy.persistence.model.base.SearchCriteria;
import com.roomx.infrastructure.multitenancy.persistence.model.entity.EquipmentEntity;
import com.roomx.infrastructure.multitenancy.persistence.model.entity.ServiceEntity;
import com.roomx.infrastructure.multitenancy.persistence.repository.jpa.JpaEquipmentEntityRepository;
import com.roomx.infrastructure.multitenancy.persistence.service.EquipmentEntityService;
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
public class EquipmentEntityServiceImpl implements EquipmentEntityService {

    private final JpaEquipmentEntityRepository jpaEquipmentEntityRepository;
    private final EquipmentEntityMapper equipmentEntityMapper;

    @Override
    public Page<Equipment> filterPageEquipments(EquipmentFilter filter, Pageable pageable, boolean typeCompare) {
        // use filter search like, AND
        String method = "%";
        boolean condition = typeCompare; // TRUE = OR || FALSE = AND
        List<SearchCriteria> filters = Stream.of(
                    new AbstractMap.SimpleEntry<>("id", filter.getId()),
                        new AbstractMap.SimpleEntry<>("id", filter.getId()),
                        new AbstractMap.SimpleEntry<>("equipmentCode", filter.getEquipmentCode()),
                        new AbstractMap.SimpleEntry<>("name", filter.getName()),
                        new AbstractMap.SimpleEntry<>("brand", filter.getBrand()),
                        new AbstractMap.SimpleEntry<>("unitPrice", filter.getUnitPrice()),
                        new AbstractMap.SimpleEntry<>("createdAt", filter.getCreatedAt()),
                        new AbstractMap.SimpleEntry<>("updatedAt", filter.getUpdatedAt())
                ).filter(entry -> entry.getValue() != null && !entry.getValue().toString().isBlank())
                .map(entry -> new SearchCriteria(entry.getKey(), entry.getValue(), method, condition))
                .toList();

        log.info("Searching data: {}", filters);

        Specification<EquipmentEntity> spec = new GenericSpecification<>(filters);

        var equipmentEntityPage = jpaEquipmentEntityRepository.findAll(spec, pageable);


        return equipmentEntityPage.map(equipmentEntityMapper::toDomain);
    }
}
