package com.roomx.infrastructure.persistence.model.projection;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.roomx.domain.model.aggrerate.Equipment;
import com.roomx.domain.model.aggrerate.Service;
import com.roomx.shared.dto.resource.base.EquipmentPriceDto;
import com.roomx.shared.dto.resource.base.ServicePriceDto;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public interface RoomProjection {
    UUID getId();
    String getRoomCode();
    String getStatus();
    String getDescription();
    List<String> getImageUrls();
    UUID getFloorPlaceId();
    UUID getBuildingPlaceId();
    UUID getBranchPlaceId();

    UUID getRoomClassId();
    String getRoomClassCode();
    Integer getCapacity();
    BigDecimal getTotalPrice();

    @JsonIgnore
    String getServicesJson();
    @JsonIgnore
    String getEquipmentsJson();

    default List<ServicePriceDto> getServices() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(getServicesJson(), new TypeReference<>() {});
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    default List<EquipmentPriceDto> getEquipments() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(getEquipmentsJson(), new TypeReference<>() {});
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

}
