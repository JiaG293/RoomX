package com.roomx.domain.repository;

import com.roomx.domain.model.entity.EquipmentRoomClass;
import com.roomx.domain.model.vo.EquipmentRoomClassId;

import java.util.List;

public interface EquipmentRoomClassRepository {
    boolean checkExistsByEquipmentRoomClassId(EquipmentRoomClassId id);
    EquipmentRoomClass save(EquipmentRoomClass equipmentRoomClass);
    List<EquipmentRoomClass> saveAll(List<EquipmentRoomClass> listEquipmentRoomClass);

    List<EquipmentRoomClass> findAllByRoomClassId(String roomClassId);
}
