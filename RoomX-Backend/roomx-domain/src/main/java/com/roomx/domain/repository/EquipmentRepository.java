package com.roomx.domain.repository;

import com.roomx.domain.model.aggrerate.Equipment;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface EquipmentRepository {
    Optional<Equipment> findById(String id);
    Optional<Equipment> findByEquipmentCode(String equipmentCode);
    Equipment save(Equipment equipment);
    void delete(Equipment equipment);
    void deleteById(String id);

    List<Equipment> saveAll(List<Equipment> listEquipmentDeleted);

    Optional<Equipment> findByIdAndStatus(String equipmentId, String status);
}
