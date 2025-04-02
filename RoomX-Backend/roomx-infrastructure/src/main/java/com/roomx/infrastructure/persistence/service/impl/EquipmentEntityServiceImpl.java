package com.roomx.infrastructure.persistence.service.impl;

import com.roomx.domain.model.aggrerate.Equipment;
import com.roomx.infrastructure.persistence.dto.EquipmentFilter;
import com.roomx.infrastructure.persistence.mapper.EquipmentEntityMapper;
import com.roomx.infrastructure.persistence.repository.jpa.JpaEquipmentEntityRepository;
import com.roomx.infrastructure.persistence.repository.specification.EquipmentSpecification;
import com.roomx.infrastructure.persistence.service.EquipmentEntityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
