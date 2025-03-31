package com.roomx.infrastructure.multitenancy.persistence.service.impl;

import com.roomx.domain.model.aggrerate.Equipment;
import com.roomx.infrastructure.multitenancy.persistence.dto.EquipmentFilter;
import com.roomx.infrastructure.multitenancy.persistence.mapper.EquipmentEntityMapper;
import com.roomx.infrastructure.multitenancy.persistence.model.base.GenericSpecification;
import com.roomx.infrastructure.multitenancy.persistence.model.base.SearchCriteria;
import com.roomx.infrastructure.multitenancy.persistence.model.entity.EquipmentEntity;
import com.roomx.infrastructure.multitenancy.persistence.repository.jpa.JpaEquipmentEntityRepository;
import com.roomx.infrastructure.multitenancy.persistence.repository.specification.EquipmentSpecification;
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
    public Page<Equipment> filterPageEquipments(EquipmentFilter filter, Pageable pageable) {
        var spec = EquipmentSpecification.searchFilterEquipment(filter);

        return jpaEquipmentEntityRepository
                .findAll(spec, pageable)
                .map(equipmentEntityMapper::toDomainSpec);
    }
}
