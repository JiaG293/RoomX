package com.roomx.domain.repository;

import com.roomx.domain.model.entity.EquipmentRequest;
import com.roomx.domain.model.vo.EquipmentRequestId;

import java.util.List;
import java.util.Optional;

public interface EquipmentRequestRepository {
    Optional<EquipmentRequest> findById(EquipmentRequestId equipmentRequestId);
    Optional<EquipmentRequest> findByEquipmentId(String equipmentId);
    Optional<EquipmentRequest> findByBookingRequestId(String bookingRequestId);
    EquipmentRequest save(EquipmentRequest equipmentRequest);
    List<EquipmentRequest> saveAll(List<EquipmentRequest> listEquipmentRequest);
}
