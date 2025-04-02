package com.roomx.infrastructure.persistence.service;

import com.roomx.domain.model.aggrerate.Equipment;
import com.roomx.infrastructure.persistence.dto.EquipmentFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;



public interface EquipmentEntityService {
    Page<Equipment> filterPageEquipments(EquipmentFilter filter, Pageable pageable);
}
