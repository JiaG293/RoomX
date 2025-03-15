package com.roomx.domain.repository;

import com.roomx.domain.model.entity.EquipmentRoomClass;
import com.roomx.domain.model.entity.ServiceRoomClass;
import com.roomx.domain.model.vo.ServiceRoomClassId;

import java.util.List;

public interface ServiceRoomClassRepository {
    ServiceRoomClass save(ServiceRoomClass serviceRoomClass);
    List<ServiceRoomClass> saveAll(List<ServiceRoomClass> serviceRoomClasses);

    boolean checkExistsByServiceRoomClassId(ServiceRoomClassId id);
    List<ServiceRoomClass> findAllByRoomClassId(String roomClassId);
}
